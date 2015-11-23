package com.goeuro.devtest.service;


import com.goeuro.devtest.AbstractUnitTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

public class DownloadPositionsFromJsonAPIServiceTest extends AbstractUnitTest {

    private DownloadPositionsFromJsonAPIServiceImpl service;

    private String destPath = "C:/temp/go-euro-test/sample/";

    @Before
    public void setup() throws IOException {
        service = ctx.getBean(DownloadPositionsFromJsonAPIServiceImpl.class);

        removeFiles(Paths.get(destPath).toFile());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCity() {
        service.downloadPositions(null, destPath);
    }

    @Test
    public void testInvalidDir() {
        service.downloadPositions("Oslo", null);
    }

    @Test
    public void testService() throws IOException {
        service.downloadPositions("Mumbai", destPath);
        String expected = getExpectedContent();
        String actual = getFirstFileContent(Paths.get(destPath).toFile());
        Assert.assertEquals(expected, actual);
    }

    private String getExpectedContent() {
        return "_id,name,type,latitude,longitude" + System.lineSeparator() +
                "457183,Mumbai,location,19.07283,72.88261" +  System.lineSeparator() +
                "315361,Mumbai,airport,19.09048,72.86713" + System.lineSeparator();
    }

}
