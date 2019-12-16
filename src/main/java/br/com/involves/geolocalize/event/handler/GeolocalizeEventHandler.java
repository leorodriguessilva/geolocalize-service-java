package br.com.involves.geolocalize.event.handler;

import br.com.involves.geolocalize.dto.GeolocalizationResultDTO;
import br.com.involves.geolocalize.event.GeolocalizationEvent;
import br.com.involves.geolocalize.event.handler.response.GeolocalizationResponse;
import br.com.involves.geolocalize.service.api.ConfigService;
import br.com.involves.geolocalize.service.api.GeolocalizationService;
import br.com.involves.geolocalize.service.impl.ConfigServiceImpl;
import br.com.involves.geolocalize.service.impl.GeolocalizeServiceImpl;
import br.com.involves.geolocalize.util.SerializationUtils;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.LinkedList;
import java.util.List;

public class GeolocalizeEventHandler implements RequestHandler<GeolocalizationEvent, GeolocalizationResponse> {

    private ConfigService configService;

    public void setConfigService(ConfigService configService) {
        this.configService = configService;
    }

    public GeolocalizationResponse handleRequest(GeolocalizationEvent event, Context context) {
        if (configService == null) {
            configService = new ConfigServiceImpl();
        }

        GeolocalizationService service = new GeolocalizeServiceImpl(event.getTypeCache(), configService);

        int amountQueriesProcessing = configService.getAmountQueriesProcessing();
        String[] geolocalizationQueries = event.getQueries();
        List<GeolocalizationResultDTO> resultQueries = new LinkedList<>();
        List<String> queriesNotProcessed = new LinkedList<>();

        for (int i = 0; i < geolocalizationQueries.length; i++) {
            String geolocalizationQuery = geolocalizationQueries[i];
            if (i >= amountQueriesProcessing) {
                queriesNotProcessed.add(geolocalizationQuery);
                continue;
            }

            GeolocalizationResultDTO latlon = service.geolocalize(geolocalizationQuery);
            resultQueries.add(latlon);
        }

        if(queriesNotProcessed.size() > 0) {
            String message = String.format("Reached max limit of queries of %s ignoring queries %s",
                    amountQueriesProcessing,
                    SerializationUtils.deserialize(queriesNotProcessed));
            System.out.println(message);
        }

        return new GeolocalizationResponse(200, resultQueries);
    }

}
