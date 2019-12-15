package br.com.involves.geolocalize.service.impl;

import br.com.involves.geolocalize.dao.api.CacheDao;
import br.com.involves.geolocalize.dao.api.DaoFactory;
import br.com.involves.geolocalize.dao.api.PersistentDao;
import br.com.involves.geolocalize.dao.impl.GeolocalizationApiResultDaoFactory;
import br.com.involves.geolocalize.domain.GeolocalizationApiResult;
import br.com.involves.geolocalize.service.api.EnvironmentConfigService;
import br.com.involves.geolocalize.service.api.GeolocalizationCacheService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Calendar;
import java.util.Date;

public class GeolocalizationCacheServiceImpl implements GeolocalizationCacheService {

    private final Logger logger = LogManager.getLogger(GeolocalizationCacheServiceImpl.class);

    private DaoFactory daoFactory;

    private EnvironmentConfigService environmentConfigService;

    public GeolocalizationCacheServiceImpl(int typeCache, EnvironmentConfigService environmentConfigService) {
        this.daoFactory = new GeolocalizationApiResultDaoFactory(typeCache, environmentConfigService);
        this.environmentConfigService = environmentConfigService;
    }

    @Override
    public void persist(GeolocalizationApiResult geolocalizationApiResult) {
        PersistentDao persistentDao = daoFactory.createPersistentDao();
        geolocalizationApiResult.setExpireAt(getPersistedExpiringDate());
        persistentDao.save(geolocalizationApiResult);
    }

    @Override
    public void cache(GeolocalizationApiResult geolocalizationApiResult) {
        CacheDao cacheDao = daoFactory.createCacheDao();
        cacheDao.save(geolocalizationApiResult);
    }

    @Override
    public GeolocalizationApiResult findCachedByQuery(String query) {
        CacheDao cacheDao = daoFactory.createCacheDao();
        return cacheDao.findByQuery(query);
    }

    @Override
    public GeolocalizationApiResult findPersistedByQuery(String query) {
        PersistentDao persistentDao = daoFactory.createPersistentDao();
        GeolocalizationApiResult result = persistentDao.findByQuery(query);
        if(isExpired(result.getExpireAt())) {
            persistentDao.deleteByQuery(query);
            return null;
        }
        return result;
    }

    @Override
    public void close() {
        try {
            daoFactory.close();
        } catch (Exception ex) {
            logger.error(ex);
        }
    }

    private boolean isExpired(Date expiringDate) {
        return new Date().compareTo(expiringDate) >= 0;
    }

    private Date getPersistedExpiringDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, environmentConfigService.getExpireDatabaseDays());
        calendar.add(Calendar.MONTH, environmentConfigService.getExpireDatabaseMonths());
        calendar.add(Calendar.YEAR, environmentConfigService.getExpireDatabaseYears());

        return calendar.getTime();
    }
}
