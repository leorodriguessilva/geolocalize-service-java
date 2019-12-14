package br.com.involves.geolocalize.service.api;

import br.com.involves.geolocalize.domain.GeolocalizationApiResult;

public class GeolocalizationCacheServiceImpl implements GeolocalizationCacheService {
    @Override
    public void persist(GeolocalizationApiResult geolocalizationApiResult) {

    }

    @Override
    public void cache(GeolocalizationApiResult geolocalizationApiResult) {

    }

    @Override
    public GeolocalizationApiResult findCachedByQuery(String query) {
        return null;
    }

    @Override
    public GeolocalizationApiResult findPersistedByQuery(String query) {
        return null;
    }
}
