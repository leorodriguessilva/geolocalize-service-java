package br.com.involves.geolocalize.event.handler.response;

import br.com.involves.geolocalize.dto.GeolocalizationResultDTO;

import java.util.List;

public class GeolocalizationResponse {

    private int statusCode;

    private List<GeolocalizationResultDTO> results;

    public GeolocalizationResponse(int statusCode, List<GeolocalizationResultDTO> results) {
        this.statusCode = statusCode;
        this.results = results;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public List<GeolocalizationResultDTO> getQueries() {
        return results;
    }

    public void setQueries(List<GeolocalizationResultDTO> results) {
        this.results = results;
    }
}
