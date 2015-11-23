package com.goeuro.devtest.service;

import com.goeuro.devtest.json.JsonLocationInfo;

public interface CsvGeneratorService {

    void generateCsv(String fineNamePrefix, String csvFileLocation, JsonLocationInfo[] jsonLocationInfoArr);
}
