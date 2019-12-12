package br.com.involves.geolocalize.dao.api;

import br.com.involves.geolocalize.domain.GeolocalizationApiResult;

public interface GeolocalizationApiResultCacheDao extends AutoCloseable {

    void save(GeolocalizationApiResult geolocalizationApiResult);

    GeolocalizationApiResult findByQuery(String query);

}
