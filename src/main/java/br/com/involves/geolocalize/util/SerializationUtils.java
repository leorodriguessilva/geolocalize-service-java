package br.com.involves.geolocalize.util;

import com.google.gson.Gson;

public final class SerializationUtils {

    public static <T> T serialize(String json, Class<T> type) {
        Gson gson = new Gson();
        T obj = gson.fromJson(json, type);
        return obj;
    }

    public static <T> String deserialize(T obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

}
