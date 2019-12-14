package br.com.involves.geolocalize.dao.api;

import br.com.involves.geolocalize.domain.GeolocalizationApiResult;

public interface PersistentDao extends AutoCloseable {

    boolean save(GeolocalizationApiResult geolocalizationApiResult);

    boolean deleteByQuery(String query);

    GeolocalizationApiResult findByQuery(String query);

}
