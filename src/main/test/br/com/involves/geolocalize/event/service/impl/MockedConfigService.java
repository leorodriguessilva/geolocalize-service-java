package br.com.involves.geolocalize.event.service.impl;

import br.com.involves.geolocalize.constants.EnvironmentConfigConstants;
import br.com.involves.geolocalize.service.api.ConfigService;

import java.util.HashMap;
import java.util.Map;

public class MockedConfigService implements ConfigService {

    private Map<String, String> configs;

    public MockedConfigService() {
        configs = new HashMap<>();
    }

    public MockedConfigService(Map<String, String> configs) {
        this.configs = configs;
    }

    @Override
    public String getDatabaseHost() {
        return getConfig(EnvironmentConfigConstants.DATABASE_SERVER_ADDRESS);
    }

    private String getConfig(String configName) {
        if (configs.containsKey(configName)) {
            return configs.get(configName);
        }
        return "0";
    }

    @Override
    public int getDatabasePort() {
        int port = Integer.parseInt(getConfig(EnvironmentConfigConstants.DATABASE_SERVER_PORT));
        return port;
    }

    @Override
    public String getDatabaseUser() {
        return getConfig(EnvironmentConfigConstants.DATABASE_USER);
    }

    @Override
    public String getDatabasePass() {
        return getConfig(EnvironmentConfigConstants.DATABASE_PASS);
    }

    @Override
    public String getTableName() {
        return getConfig(EnvironmentConfigConstants.PERSISTENT_CACHE_TABLE);
    }

    @Override
    public String getCacheServerHost() {
        return getConfig(EnvironmentConfigConstants.CACHE_SERVER_ADDRESS);
    }

    @Override
    public int getCacheServerPort() {
        int port = Integer.parseInt(getConfig(EnvironmentConfigConstants.CACHE_SERVER_PORT));
        return port;
    }

    @Override
    public String getCacheServerPass() {
        return getConfig(EnvironmentConfigConstants.CACHE_SERVER_PASS);
    }

    @Override
    public int getCacheExpirationInSeconds() {
        int cacheExpirationInSeconds = Integer.parseInt(getConfig(EnvironmentConfigConstants.CACHE_EXPIRATION_IN_SECONDS));
        return cacheExpirationInSeconds;
    }

    @Override
    public int getExpireDatabaseDays() {
        int expireDatabaseDays = Integer.parseInt(getConfig(EnvironmentConfigConstants.EXPIRE_DATABASE_DAYS));
        return expireDatabaseDays;
    }

    @Override
    public int getExpireDatabaseMonths() {
        int expireDatabaseMonths = Integer.parseInt(getConfig(EnvironmentConfigConstants.EXPIRE_DATABASE_MONTHS));
        return expireDatabaseMonths;
    }

    @Override
    public int getExpireDatabaseYears() {
        int expireDatabaseYears = Integer.parseInt(getConfig(EnvironmentConfigConstants.EXPIRE_DATABASE_YEARS));
        return expireDatabaseYears;
    }

    @Override
    public String getMapsApiKey() {
        return getConfig(EnvironmentConfigConstants.MAPS_API_KEY);
    }

    @Override
    public String getAwsRegion() {
        return getConfig(EnvironmentConfigConstants.AWS_REGION);
    }

    @Override
    public boolean getUseDynamoDb() {
        boolean useDynamoDb = Boolean.parseBoolean(getConfig(EnvironmentConfigConstants.USE_DYNAMODB));
        return useDynamoDb;
    }

    @Override
    public boolean getLogPersistentCache() {
        boolean logPersistentCache = Boolean.parseBoolean(getConfig(EnvironmentConfigConstants.LOG_PERSISTENT_CACHE));
        return logPersistentCache;
    }

    @Override
    public int getAmountQueriesProcessing() {
        int amountQueriesProcessing = Integer.parseInt(getConfig(EnvironmentConfigConstants.AMOUNT_QUERIES_PROCESSING));
        return amountQueriesProcessing;
    }
}
