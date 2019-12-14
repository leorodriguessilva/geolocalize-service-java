package br.com.involves.geolocalize.dao.impl;

import br.com.involves.geolocalize.dao.api.PersistentDao;
import br.com.involves.geolocalize.domain.GeolocalizationApiResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GeolocalizationApiResultLoggerDao implements PersistentDao {

    static final Logger logger = LogManager.getLogger(GeolocalizationApiResultLoggerDao.class);

    private PersistentDao wrapped;

    public GeolocalizationApiResultLoggerDao(PersistentDao wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public boolean save(GeolocalizationApiResult geolocalizationApiResult) {
        boolean success = wrapped.save(geolocalizationApiResult);
        if(success) {
            logger.info(String.format("Adding new geolocalization for query %s to database", geolocalizationApiResult.getQuery()));
        }
        return false;
    }

    @Override
    public boolean deleteByQuery(String query) {
        boolean success = wrapped.deleteByQuery(query);
        if(success) {
            logger.info(String.format("Deleting expired geolocalization for query %s from database", query));
        }
        return false;
    }

    @Override
    public GeolocalizationApiResult findByQuery(String query) {
        GeolocalizationApiResult result = wrapped.findByQuery(query);
        if(result != null) {
            logger.info(String.format("Retrieving geolocalization for query %s from database", query));
        }
        return result;
    }

    @Override
    public void close() throws Exception {
        wrapped.close();
    }
}
