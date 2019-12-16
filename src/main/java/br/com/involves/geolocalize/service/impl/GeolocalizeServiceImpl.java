package br.com.involves.geolocalize.service.impl;

import br.com.involves.geolocalize.domain.GeolocalizationApiResult;
import br.com.involves.geolocalize.dto.GeolocalizationResultDTO;
import br.com.involves.geolocalize.service.api.ConfigService;
import br.com.involves.geolocalize.service.api.GeolocalizationCacheService;
import br.com.involves.geolocalize.service.api.GeolocalizationService;

import java.util.Random;

public class GeolocalizeServiceImpl implements GeolocalizationService {

    private int typeCache;

    private ConfigService configService;

    public GeolocalizeServiceImpl(int typeCache, ConfigService configService) {
        this.typeCache = typeCache;
        this.configService = configService;
    }

    @Override
    public GeolocalizationResultDTO geolocalize(String query) {
        try (GeolocalizationCacheService cacheService = new GeolocalizationCacheServiceImpl(typeCache, configService)) {
            return geolocalizeByQuery(query, cacheService);
        } catch(Exception ex) {
            System.err.println(String.format("Unidentified error when closing cache service: %s", ex));
        }
        return null;
    }

    private GeolocalizationResultDTO geolocalizeByQuery(String query, GeolocalizationCacheService cacheService) {
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
        System.out.println("Finded latlon by external api");

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
