package br.com.involves.geolocalize.event.handler.test;

import br.com.involves.geolocalize.constants.EnvironmentConfigConstants;
import br.com.involves.geolocalize.dto.TypeCache;
import br.com.involves.geolocalize.event.GeolocalizationEvent;
import br.com.involves.geolocalize.event.handler.GeolocalizeEventHandler;
import br.com.involves.geolocalize.event.handler.context.impl.MockedContext;
import br.com.involves.geolocalize.event.handler.response.GeolocalizationResponse;
import br.com.involves.geolocalize.event.service.impl.MockedConfigService;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class GeolocalizeEventHandlerTest {

    @Test
    public void simpleLoadTest() {
        Map<String, String> configs = new HashMap<>();
        configs.put(EnvironmentConfigConstants.AMOUNT_QUERIES_PROCESSING, "100");
        GeolocalizeEventHandler tested = new GeolocalizeEventHandler();
        tested.setConfigService(new MockedConfigService(configs));
        GeolocalizationEvent event = new GeolocalizationEvent();
        event.setTypeCache(TypeCache.NO_CACHE.getValue());
        String[] queries = new String[100];
        for (int i = 0; i < queries.length; i++) {
            queries[i] = new StringBuilder().append("?query").append(i).toString();
        }
        event.setQueries(queries);
        GeolocalizationResponse response = tested.handleRequest(event, new MockedContext());
        Assert.assertEquals(100, response.getQueries().size());
    }

}
