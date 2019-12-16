package br.com.involves.geolocalize.dao.impl;

import br.com.involves.geolocalize.dao.api.PersistentDao;
import br.com.involves.geolocalize.domain.GeolocalizationApiResult;

public class GeolocalizationApiResultLoggerDao implements PersistentDao {

    private PersistentDao wrapped;

    public GeolocalizationApiResultLoggerDao(PersistentDao wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public void connect() {
        try {
            wrapped.connect();
        } catch(Exception ex) {
            System.err.println(String.format("Error when connecting to database", ex));
        }
    }

    @Override
    public boolean save(GeolocalizationApiResult geolocalizationApiResult) {
        boolean success = wrapped.save(geolocalizationApiResult);
        if(success) {
            System.out.println(String.format("Adding new geolocalization for query %s to database", geolocalizationApiResult.getQuery()));
        }
        return false;
    }

    @Override
    public boolean deleteByQuery(String query) {
        boolean success = wrapped.deleteByQuery(query);
        if(success) {
            System.out.println(String.format("Deleting expired geolocalization for query %s from database", query));
        }
        return false;
    }

    @Override
    public GeolocalizationApiResult findByQuery(String query) {
        GeolocalizationApiResult result = null;
        try {
            result = wrapped.findByQuery(query);
        } catch(Exception ex) {
            System.err.println(String.format("error when retrieving for query %s, error: %s", query, ex));
        }

        if(result != null) {
            System.out.println(String.format("Retrieving geolocalization for query %s from database", query));
        }
        return result;
    }

    @Override
    public void close() throws Exception {
        wrapped.close();
    }
}
