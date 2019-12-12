package br.com.involves.geolocalize.event;

public class GeolocalizationEvent {

    private Integer typeCache;

    private String[] queries;

    public Integer getTypeCache() {
        return typeCache;
    }

    public void setTypeCache(Integer typeCache) {
        this.typeCache = typeCache;
    }

    public String[] getQueries() {
        return queries;
    }

    public void setQueries(String[] queries) {
        this.queries = queries;
    }
}
