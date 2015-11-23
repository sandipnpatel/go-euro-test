package com.goeuro.devtest.service;

import com.goeuro.devtest.json.JsonLocationInfo;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DownloadPositionsFromJsonAPIServiceImpl implements DownloadPositionsFromJsonAPIService{

    private final static Logger logger = Logger.getLogger(CsvGeneratorServiceImpl.class);

    @Autowired
    private String jsonAPI;

    @Autowired
    private String csvOutputDir;

    @Autowired
    private HttpURLConnectionService httpURLConnectionService;

    @Autowired
    private CsvGeneratorService csvGeneratorService;

    @Override
    public void downloadPositions(String city, String csvFileLocation) {

        if (Strings.isNullOrEmpty(city) ) {
            throw new IllegalArgumentException(String.format("Invalid value of city '%s'", city));
        }

        // If destination dir is not provided as second argument in command line, then get it from application.properties file.
        if (Strings.isNullOrEmpty(csvFileLocation) ) {
            csvFileLocation = csvOutputDir;
            logger.info("Used destination directory from 'application.properties'.");
        } else {
            logger.info("Used destination directory provided in command line argument.");
        }

        logger.info(String.format("Retrieve date from JSON API. City: '%s'",city));
        String url = jsonAPI + city;
        String response = httpURLConnectionService.sendGet(url);

        JsonLocationInfo[] jsonLocationInfoArr = new Gson().fromJson(response, JsonLocationInfo[].class);

        logger.info("Export date to .csv file.");
        csvGeneratorService.generateCsv(city, csvFileLocation, jsonLocationInfoArr);
    }
}
