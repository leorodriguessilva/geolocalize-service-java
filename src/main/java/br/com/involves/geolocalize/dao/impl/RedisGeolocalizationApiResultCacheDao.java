package br.com.involves.geolocalize.dao.impl;

import br.com.involves.geolocalize.dao.api.GeolocalizationApiResultCacheDao;
import br.com.involves.geolocalize.domain.GeolocalizationApiResult;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import redis.clients.jedis.Jedis;

import java.io.IOException;

public class RedisGeolocalizationApiResultCacheDao implements GeolocalizationApiResultCacheDao {

    private Jedis jedis;

    public RedisGeolocalizationApiResultCacheDao(String host, int port, String pass) {
        jedis = new Jedis(host, port);
        jedis.auth(pass);
    }

    public void save(GeolocalizationApiResult geolocalizationApiResult) {
        Gson gson = new Gson();
        jedis.set(geolocalizationApiResult.getQuery(), gson.toJson(geolocalizationApiResult));
    }

    public GeolocalizationApiResult findByQuery(String query) {
        String result = jedis.get(query);
        if(Strings.isNullOrEmpty(result)) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(result, GeolocalizationApiResult.class);
    }

    public void close() throws IOException {
        jedis.close();
    }
}