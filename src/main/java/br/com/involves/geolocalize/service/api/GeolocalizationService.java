package br.com.involves.geolocalize.service.api;

import br.com.involves.geolocalize.dto.GeolocalizationResultDTO;

public interface GeolocalizationService {

    GeolocalizationResultDTO geolocalizeByQuery(String query);

}
