package br.com.involves.geolocalize.dao.impl;

import br.com.involves.geolocalize.dao.api.CacheDao;
import br.com.involves.geolocalize.dao.api.DaoFactory;
import br.com.involves.geolocalize.dao.api.PersistentDao;
import br.com.involves.geolocalize.dto.TypeCache;
import br.com.involves.geolocalize.service.api.ConfigService;

public class GeolocalizationApiResultDaoFactory implements DaoFactory {

    private PersistentDao persistentDao;

    private CacheDao cacheDao;

    private int typeCache;

    private ConfigService configService;

    public GeolocalizationApiResultDaoFactory(int typeCache, ConfigService configService) {
        this.typeCache = typeCache;
        this.configService = configService;
        mapCacheDao();
        mapPersistentDao();
    }

    public CacheDao createCacheDao() {
        return cacheDao;
    }

    public PersistentDao createPersistentDao() {
        return persistentDao;
    }

    @Override
    public void close() throws Exception {
        try {
            persistentDao.close();
            cacheDao.close();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private void mapPersistentDao() {
        if(typeCache < TypeCache.ONLY_PERSISTENT_CACHE.getValue()) {
            persistentDao = new NoDatabaseGeolocalizationApiResultDao();
            return;
        }

        persistentDao = new CassandraGeolocalizationApiResultDao(
                configService.getDatabaseHost(),
                configService.getDatabasePort(),
                configService.getDatabaseUser(),
                configService.getDatabasePass(),
                configService.getTableName());
        if(configService.getUseDynamoDb()) {
            persistentDao = new DynamoDBGeolocalizationApiResultDao(
                    configService.getTableName(),
                    configService.getAwsRegion());
        }

        PersistentDao loggerWrapper = persistentDao;
        if(configService.getLogPersistentCache()) {
            loggerWrapper = new GeolocalizationApiResultLoggerDao(persistentDao);
        }

        persistentDao = loggerWrapper;
        persistentDao.connect();
    }

    private void mapCacheDao() {
        if(typeCache == TypeCache.ONLY_MEMORY_CACHE.getValue()
                || typeCache == TypeCache.MEMORY_AND_PERSISTENT_CACHE.getValue()) {

            cacheDao = new RedisGeolocalizationApiResultCacheDao(
                    configService.getCacheServerHost(),
                    configService.getCacheServerPort(),
                    configService.getCacheServerPass()
            );
            return;
        }
        cacheDao = new NoGeolocalizationApiResultCacheDao();
    }
}
