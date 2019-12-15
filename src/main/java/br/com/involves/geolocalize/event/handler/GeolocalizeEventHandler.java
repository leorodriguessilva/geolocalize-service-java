package br.com.involves.geolocalize.event.handler;

import br.com.involves.geolocalize.dto.GeolocalizationResultDTO;
import br.com.involves.geolocalize.event.GeolocalizationEvent;
import br.com.involves.geolocalize.event.handler.response.GeolocalizationResponse;
import br.com.involves.geolocalize.service.api.EnvironmentConfigService;
import br.com.involves.geolocalize.service.api.GeolocalizationService;
import br.com.involves.geolocalize.service.impl.EnvironmentConfigServiceImpl;
import br.com.involves.geolocalize.service.impl.GeolocalizeServiceImpl;
import br.com.involves.geolocalize.util.SerializationUtils;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

public class GeolocalizeEventHandler implements RequestHandler<GeolocalizationEvent, GeolocalizationResponse> {

    private final Logger logger = LogManager.getLogger(GeolocalizeEventHandler.class);

    public GeolocalizationResponse handleRequest(GeolocalizationEvent event, Context context) {
        EnvironmentConfigService configService = new EnvironmentConfigServiceImpl();
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
            logger.warn(message);
        }

        return new GeolocalizationResponse(200, resultQueries);
    }

}
