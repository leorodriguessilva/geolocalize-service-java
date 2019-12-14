package br.com.involves.geolocalize.service.impl;

import br.com.involves.geolocalize.constants.EnvironmentConfigConstants;
import br.com.involves.geolocalize.service.api.EnvironmentConfigService;

public class EnvironmentConfigServiceImpl implements EnvironmentConfigService {

    @Override
    public String getDatabaseHost() {
        return System.getenv().get(EnvironmentConfigConstants.DATABASE_SERVER_ADDRESS);
    }

    @Override
    public int getDatabasePort() {
        int port = Integer.parseInt(System.getenv().get(EnvironmentConfigConstants.DATABASE_SERVER_PORT));
        return port;
    }

    @Override
    public String getDatabaseUser() {
        return System.getenv().get(EnvironmentConfigConstants.DATABASE_USER);
    }

    @Override
    public String getDatabasePass() {
        return System.getenv().get(EnvironmentConfigConstants.DATABASE_PASS);
    }

    @Override
    public String getTableName() {
        return System.getenv().get(EnvironmentConfigConstants.PERSISTENT_CACHE_TABLE);
    }

    @Override
    public String getCacheServerHost() {
        return System.getenv().get(EnvironmentConfigConstants.CACHE_SERVER_ADDRESS);
    }

    @Override
    public int getCacheServerPort() {
        int port = Integer.parseInt(System.getenv().get(EnvironmentConfigConstants.CACHE_SERVER_PORT));
        return port;
    }

    @Override
    public String getCacheServerPass() {
        return System.getenv().get(EnvironmentConfigConstants.CACHE_SERVER_PASS);
    }

    @Override
    public int getCacheExpirationInSeconds() {
        int expirationInSeconds = Integer.parseInt(System.getenv().get(EnvironmentConfigConstants.CACHE_EXPIRATION_IN_SECONDS));
        return expirationInSeconds;
    }

    @Override
    public int getExpireDatabaseDays() {
        int expireDatabaseDays = Integer.parseInt(System.getenv().get(EnvironmentConfigConstants.EXPIRE_DATABASE_DAYS));
        return expireDatabaseDays;
    }

    @Override
    public int getExpireDatabaseMonths() {
        int expireDatabaseMonths = Integer.parseInt(System.getenv().get(EnvironmentConfigConstants.EXPIRE_DATABASE_MONTHS));
        return expireDatabaseMonths;
    }

    @Override
    public int getExpireDatabaseYears() {
        int expireDatabaseYears = Integer.parseInt(System.getenv().get(EnvironmentConfigConstants.EXPIRE_DATABASE_YEARS));
        return expireDatabaseYears;
    }

    @Override
    public String getMapsApiKey() {
        return System.getenv().get(EnvironmentConfigConstants.MAPS_API_KEY);
    }

    @Override
    public String getAwsRegion() {
        return System.getenv(EnvironmentConfigConstants.AWS_REGION);
    }

    @Override
    public boolean getUseDynamoDb() {
        boolean useDynamoDb = Boolean.parseBoolean(System.getenv().get(EnvironmentConfigConstants.USE_DYNAMODB));
        return useDynamoDb;
    }

    @Override
    public boolean getLogPersistentCache() {
        boolean logPersistentCache = Boolean.parseBoolean(System.getenv().get(EnvironmentConfigConstants.LOG_PERSISTENT_CACHE));
        return logPersistentCache;
    }
}
