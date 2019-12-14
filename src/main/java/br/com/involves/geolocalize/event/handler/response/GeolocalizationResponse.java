package br.com.involves.geolocalize.event.handler.response;

import br.com.involves.geolocalize.dto.GeolocalizationResultDTO;

public class GeolocalizationResponse {

    private GeolocalizationResultDTO[] results;

    public GeolocalizationResultDTO[] getResults() {
        return results;
    }

    public void setResults(GeolocalizationResultDTO[] results) {
        this.results = results;
    }
}
