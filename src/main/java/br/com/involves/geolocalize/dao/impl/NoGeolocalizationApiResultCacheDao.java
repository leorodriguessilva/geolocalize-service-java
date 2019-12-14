package br.com.involves.geolocalize.dao.impl;

import br.com.involves.geolocalize.dao.api.CacheDao;
import br.com.involves.geolocalize.domain.GeolocalizationApiResult;

public class NoGeolocalizationApiResultCacheDao implements CacheDao {
    @Override
    public void save(GeolocalizationApiResult geolocalizationApiResult) { }

    @Override
    public GeolocalizationApiResult findByQuery(String query) {
        return null;
    }

    @Override
    public void close() throws Exception { }
}
