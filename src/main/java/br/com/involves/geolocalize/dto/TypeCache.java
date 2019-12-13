package br.com.involves.geolocalize.dto;

public enum TypeCache {

    NO_CACHE(0),
    ONLY_MEMORY_CACHE(1),
    ONLY_PERSISTENT_CACHE(2),
    MEMORY_AND_PERSISTENT_CACHE(3);

    private int value;

    private TypeCache(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
