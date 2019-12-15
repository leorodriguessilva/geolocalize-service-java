package br.com.involves.geolocalize.service.api;

import br.com.involves.geolocalize.domain.GeolocalizationApiResult;

public interface GeolocalizationCacheService extends AutoCloseable {

    void persist(GeolocalizationApiResult geolocalizationApiResult);

    void cache(GeolocalizationApiResult geolocalizationApiResult);

    GeolocalizationApiResult  findCachedByQuery(String query);

    GeolocalizationApiResult  findPersistedByQuery(String query);

}
