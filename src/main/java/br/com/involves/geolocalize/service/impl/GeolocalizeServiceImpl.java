package br.com.involves.geolocalize.service.impl;

import br.com.involves.geolocalize.domain.GeolocalizationApiResult;
import br.com.involves.geolocalize.dto.GeolocalizationResultDTO;
import br.com.involves.geolocalize.service.api.EnvironmentConfigService;
import br.com.involves.geolocalize.service.api.GeolocalizationCacheService;
import br.com.involves.geolocalize.service.api.GeolocalizationService;

import java.util.Random;

public class GeolocalizeServiceImpl implements GeolocalizationService {

    private GeolocalizationCacheService cacheService;

    public GeolocalizeServiceImpl(int typeCache, EnvironmentConfigService environmentConfigService) {
        this.cacheService = new GeolocalizationCacheServiceImpl(typeCache, environmentConfigService);
    }

    @Override
    public GeolocalizationResultDTO geolocalizeByQuery(String query) {
        GeolocalizationApiResult result = cacheService.findCachedByQuery(query);

        if(result != null) {
            return transformIntoDTO(result);
        }

        result = cacheService.findPersistedByQuery(query);

        if(result != null) {
            cacheService.cache(result);
            return transformIntoDTO(result);
        }

        result = findLatLonByExternalApi(query);

        if(result != null) {
            cacheService.cache(result);
            cacheService.persist(result);
        }

        return transformIntoDTO(result);
    }

    private GeolocalizationApiResult findLatLonByExternalApi(String query) {
        float lat = generateRandomDegree();
        float lon = generateRandomDegree();
        GeolocalizationApiResult result = new GeolocalizationApiResult();
        result.setQuery(query);
        result.setLatitude(lat);
        result.setLongitude(lon);
        return result;
    }

    private float generateRandomDegree() {
        Random r = new Random();
        float low = 0;
        float high = 180;
        float random = low + r.nextFloat() * (high - low);
        return random;
    }

    private GeolocalizationResultDTO transformIntoDTO(GeolocalizationApiResult geolocalizationApiResult) {
        GeolocalizationResultDTO dto = new GeolocalizationResultDTO();
        dto.setQuery(geolocalizationApiResult.getQuery());
        dto.setLongitude(geolocalizationApiResult.getLongitude());
        dto.setLatitude(geolocalizationApiResult.getLatitude());
        return dto;
    }
}
