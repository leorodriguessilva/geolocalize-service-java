package br.com.involves.geolocalize.service.api;

public interface EnvironmentConfigService {

    String getDatabaseHost();

    int getDatabasePort();

    String getDatabaseUser();

    String getDatabasePass();

    String getTableName();

    String getCacheServerHost();

    int getCacheServerPort();

    String getCacheServerPass();

    int getCacheExpirationInSeconds();

    int getExpireDatabaseDays();

    int getExpireDatabaseMonths();

    int getExpireDatabaseYears();

    String getMapsApiKey();

    String getAwsRegion();

    boolean getUseDynamoDb();

    boolean getLogPersistentCache();


}
