package br.com.involves.geolocalize.event.handler;

import br.com.involves.geolocalize.event.GeolocalizationEvent;
import br.com.involves.geolocalize.event.handler.response.GeolocalizationResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GeolocalizeEventHandler implements RequestHandler<GeolocalizationEvent, GeolocalizationResponse> {

    public GeolocalizationResponse handleRequest(GeolocalizationEvent geolocalizationEvent, Context context) {
        return null;
    }

}
