package br.com.involves.geolocalize.event.handler.response;

import br.com.involves.geolocalize.dto.GeolocalizationResult;

public class GeolocalizationResponse {

    private GeolocalizationResult[] results;

    public GeolocalizationResult[] getResults() {
        return results;
    }

    public void setResults(GeolocalizationResult[] results) {
        this.results = results;
    }
}
