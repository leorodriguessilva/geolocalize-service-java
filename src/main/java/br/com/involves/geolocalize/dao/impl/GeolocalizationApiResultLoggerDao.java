package br.com.involves.geolocalize.dao.impl;

import br.com.involves.geolocalize.dao.api.GeolocalizationApiResultDao;
import br.com.involves.geolocalize.domain.GeolocalizationApiResult;

public class GeolocalizationApiResultLoggerDao implements GeolocalizationApiResultDao {

    private GeolocalizationApiResultDao wrapped;

    public GeolocalizationApiResultLoggerDao(GeolocalizationApiResultDao wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public boolean save(GeolocalizationApiResult geolocalizationApiResult) {
        boolean success = wrapped.save(geolocalizationApiResult);
        if(success) {

        }
        return false;
    }

    @Override
    public boolean deleteByQuery(String query) {
        boolean success = wrapped.deleteByQuery(query);
        if(success) {

        }
        return false;
    }

    @Override
    public GeolocalizationApiResult findByQuery(String query) {
        GeolocalizationApiResult result = wrapped.findByQuery(query);
        if(result != null) {

        }
        return result;
    }

    @Override
    public void close() throws Exception {
        wrapped.close();
    }
}
