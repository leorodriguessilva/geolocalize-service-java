package br.com.involves.geolocalize.dao.impl;

import br.com.involves.geolocalize.dao.api.PersistentDao;
import br.com.involves.geolocalize.domain.GeolocalizationApiResult;

public class NoDatabaseGeolocalizationApiResultDao implements PersistentDao {

    @Override
    public void connect() { }

    @Override
    public boolean save(GeolocalizationApiResult geolocalizationApiResult) {
        return false;
    }

    @Override
    public boolean deleteByQuery(String query) {
        return false;
    }

    @Override
    public GeolocalizationApiResult findByQuery(String query) {
        return null;
    }

    @Override
    public void close() throws Exception { }
}
