package br.com.involves.geolocalize.dao.api;

public interface DaoFactory {

    CacheDao createCacheDao();

    PersistentDao createPersistentDao();
}
