package br.com.involves.geolocalize.domain;

import java.io.Serializable;
import java.util.Date;

public class GeolocalizationApiResult implements Serializable {

    private String query;

    private Float longitude;

    private Float latitude;

    private Date expireAt;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Date getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Date expireAt) {
        this.expireAt = expireAt;
    }
}
