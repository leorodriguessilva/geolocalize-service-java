package br.com.involves.geolocalize.dao.impl;

import br.com.involves.geolocalize.dao.api.CacheDao;
import br.com.involves.geolocalize.dao.api.DaoFactory;
import br.com.involves.geolocalize.dao.api.PersistentDao;
import br.com.involves.geolocalize.dto.TypeCache;
import br.com.involves.geolocalize.service.api.EnvironmentConfigService;

import java.util.HashMap;
import java.util.Map;

public class GeolocalizationApiResultDaoFactory implements DaoFactory {

    private Map<TypeCache, PersistentDao> persistentDaos;

    private Map<TypeCache, CacheDao> cacheDaos;

    private int typeCache;

    private EnvironmentConfigService environmentConfigService;

    public GeolocalizationApiResultDaoFactory(int typeCache, EnvironmentConfigService environmentConfigService) {
        this.typeCache = typeCache;
        this.environmentConfigService = environmentConfigService;
    }

    public CacheDao createCacheDao() {
        if(persistentDaos.containsKey(typeCache)) {
            return cacheDaos.get(typeCache);
        }
        return new NoGeolocalizationApiResultCacheDao();
    }

    public PersistentDao createPersistentDao() {
        if(persistentDaos.containsKey(typeCache)) {
            return persistentDaos.get(typeCache);
        }
        return new NoDatabaseGeolocalizationApiResultDao();
    }

    private void mapPersistentDao() {
        persistentDaos = new HashMap<>();

        PersistentDao persistentDao = new CassandraGeolocalizationApiResultDao(
                environmentConfigService.getDatabaseHost(),
                environmentConfigService.getDatabasePort(),
                environmentConfigService.getDatabaseUser(),
                environmentConfigService.getDatabasePass(),
                environmentConfigService.getTableName());
        if(environmentConfigService.getUseDynamoDb()) {
            persistentDao = new DynamoDBGeolocalizationApiResultDao(
                    environmentConfigService.getTableName(),
                    environmentConfigService.getAwsRegion());
        }

        PersistentDao loggerWrapper = persistentDao;
        if(environmentConfigService.getLogPersistentCache()) {
            loggerWrapper = new GeolocalizationApiResultLoggerDao(persistentDao);
        }

        persistentDaos.put(TypeCache.ONLY_PERSISTENT_CACHE, loggerWrapper);
        persistentDaos.put(TypeCache.MEMORY_AND_PERSISTENT_CACHE, loggerWrapper);
    }

    private void mapCacheDao() {
        cacheDaos = new HashMap<>();

        CacheDao cacheDao = new RedisGeolocalizationApiResultCacheDao(
                environmentConfigService.getCacheServerHost(),
                environmentConfigService.getCacheServerPort(),
                environmentConfigService.getCacheServerPass()
        );

        cacheDaos.put(TypeCache.ONLY_MEMORY_CACHE, cacheDao);
        cacheDaos.put(TypeCache.MEMORY_AND_PERSISTENT_CACHE, cacheDao);
    }
}
