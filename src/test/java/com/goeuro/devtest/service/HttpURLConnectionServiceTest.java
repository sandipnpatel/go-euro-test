package com.goeuro.devtest.service;

import com.goeuro.devtest.AbstractUnitTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HttpURLConnectionServiceTest extends AbstractUnitTest{

    private HttpURLConnectionServiceImpl service;

    private static final String url = "http://api.goeuro.com/api/v2/position/suggest/en/";
    @Before
    public void setup() {
        service = ctx.getBean(HttpURLConnectionServiceImpl.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidJsonURITest() {
        service.sendGet(null);
    }

    @Test
    public void testHttpURLConnectionService() {
        String cityName = "Munich";
        String response = service.sendGet(url+cityName);
        String expected = getExpectedOutput();
        Assert.assertEquals(expected, response);
    }

    private String getExpectedOutput() {
        return "[{\"_id\":376946,\"key\":null,\"name\":\"Munich\",\"fullName\":\"Munich, Germany\",\"iata_airport_code\":null,\"type\":\"location\",\"country\":\"Germany\",\"geo_position\":{\"latitude\":48.13743,\"longitude\":11.57549},\"locationId\":9120,\"inEurope\":true,\"countryCode\":\"DE\",\"coreCountry\":true,\"distance\":null},{\"_id\":313870,\"key\":null,\"name\":\"Munich\",\"fullName\":\"Munich (MUC), Germany\",\"iata_airport_code\":\"MUC\",\"type\":\"airport\",\"country\":\"Germany\",\"geo_position\":{\"latitude\":48.354597,\"longitude\":11.785065},\"locationId\":null,\"inEurope\":true,\"countryCode\":\"DE\",\"coreCountry\":true,\"distance\":null},{\"_id\":329668,\"key\":null,\"name\":\"Munich Hbf\",\"fullName\":\"Munich Hbf, Germany\",\"iata_airport_code\":null,\"type\":\"station\",\"country\":\"Germany\",\"geo_position\":{\"latitude\":48.140228,\"longitude\":11.558338},\"locationId\":null,\"inEurope\":true,\"countryCode\":\"DE\",\"coreCountry\":true,\"distance\":null}]";
    }
}
