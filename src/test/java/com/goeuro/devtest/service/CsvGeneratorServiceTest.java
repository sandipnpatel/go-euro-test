package com.goeuro.devtest.service;


import com.goeuro.devtest.AbstractUnitTest;
import com.goeuro.devtest.json.JsonLocationInfo;
import com.goeuro.devtest.json.geo_position;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

public class CsvGeneratorServiceTest  extends AbstractUnitTest {

    private CsvGeneratorServiceImpl service;

    private JsonLocationInfo[] jsonLocationInfoArr;

    private static final String destPath = "C:/temp/go-euro-test/sample/";
    @Before
    public void setup() throws IOException {
        service = ctx.getBean(CsvGeneratorServiceImpl.class);

        removeFiles(Paths.get(destPath).toFile());

        JsonLocationInfo info = new JsonLocationInfo();
        info._id = 376217;
        info.name = "Berlin";
        info.type = "location";
        info.geo_position = new geo_position();
        info.geo_position.latitude = 52.52437;
        info.geo_position.longitude = 13.41053;
        jsonLocationInfoArr = new JsonLocationInfo[] {info};
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidFineNamePrefix() {
        service.generateCsv(null, destPath, jsonLocationInfoArr);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidDestPath() {
        service.generateCsv("Berlin", null, jsonLocationInfoArr);
    }

    @Test
    public void testNoLocations() throws IOException {
        service.generateCsv("Berlin", destPath, null);
        String actual = getFirstFileContent(Paths.get(destPath).toFile());
        Assert.assertEquals("_id,name,type,latitude,longitude" + System.lineSeparator(), actual);
    }

    @Test
    public void testLocationData() throws IOException {
        service.generateCsv("Berlin", destPath, jsonLocationInfoArr);
        String actual = getFirstFileContent(Paths.get(destPath).toFile());
        Assert.assertEquals(getExpectedContent(), actual);
    }

    private String getExpectedContent() {
        return "_id,name,type,latitude,longitude" + System.lineSeparator() +
                "376217,Berlin,location,52.52437,13.41053" + System.lineSeparator();
    }


}
