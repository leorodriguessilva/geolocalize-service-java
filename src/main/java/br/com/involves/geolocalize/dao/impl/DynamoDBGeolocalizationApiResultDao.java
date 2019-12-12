package br.com.involves.geolocalize.dao.impl;

import br.com.involves.geolocalize.dao.api.GeolocalizationApiResultDao;
import br.com.involves.geolocalize.domain.GeolocalizationApiResult;

public class DynamoDBGeolocalizationApiResultDao implements GeolocalizationApiResultDao {



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
    public void close() throws Exception {

    }
}
