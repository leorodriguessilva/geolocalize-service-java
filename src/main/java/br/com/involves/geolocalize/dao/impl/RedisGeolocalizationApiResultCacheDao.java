package br.com.involves.geolocalize.dao.impl;

import br.com.involves.geolocalize.dao.api.CacheDao;
import br.com.involves.geolocalize.domain.GeolocalizationApiResult;
import br.com.involves.geolocalize.util.SerializationUtils;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import redis.clients.jedis.Jedis;

import java.io.IOException;

public class RedisGeolocalizationApiResultCacheDao implements CacheDao {

    private Jedis jedis;

    public RedisGeolocalizationApiResultCacheDao(String host, int port, String pass) {
        jedis = new Jedis(host, port);
        jedis.auth(pass);
    }

    public void save(GeolocalizationApiResult geolocalizationApiResult) {
        jedis.set(geolocalizationApiResult.getQuery(), SerializationUtils.deserialize(geolocalizationApiResult));
    }

    public GeolocalizationApiResult findByQuery(String query) {
        String result = jedis.get(query);
        if(Strings.isNullOrEmpty(result)) {
            return null;
        }
        return SerializationUtils.serialize(result, GeolocalizationApiResult.class);
    }

    public void close() throws IOException {
        jedis.close();
    }
}
