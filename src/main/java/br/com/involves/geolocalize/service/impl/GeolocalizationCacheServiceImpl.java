package br.com.involves.geolocalize.service.impl;

import br.com.involves.geolocalize.dao.api.CacheDao;
import br.com.involves.geolocalize.dao.api.DaoFactory;
import br.com.involves.geolocalize.dao.api.PersistentDao;
import br.com.involves.geolocalize.dao.impl.GeolocalizationApiResultDaoFactory;
import br.com.involves.geolocalize.domain.GeolocalizationApiResult;
import br.com.involves.geolocalize.service.api.ConfigService;
import br.com.involves.geolocalize.service.api.GeolocalizationCacheService;

import java.util.Calendar;
import java.util.Date;

public class GeolocalizationCacheServiceImpl implements GeolocalizationCacheService {

    private DaoFactory daoFactory;

    private ConfigService configService;

    public GeolocalizationCacheServiceImpl(int typeCache, ConfigService configService) {
        this.daoFactory = new GeolocalizationApiResultDaoFactory(typeCache, configService);
        this.configService = configService;
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
        if(result == null) {
            return null;
        }
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
            System.err.println(ex);
        }
    }

    private boolean isExpired(Date expiringDate) {
        return new Date().compareTo(expiringDate) >= 0;
    }

    private Date getPersistedExpiringDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, configService.getExpireDatabaseDays());
        calendar.add(Calendar.MONTH, configService.getExpireDatabaseMonths());
        calendar.add(Calendar.YEAR, configService.getExpireDatabaseYears());

        return calendar.getTime();
    }
}
