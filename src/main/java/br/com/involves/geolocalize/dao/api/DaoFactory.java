package br.com.involves.geolocalize.dao.api;

public interface DaoFactory extends AutoCloseable {

    CacheDao createCacheDao();

    PersistentDao createPersistentDao();

}
