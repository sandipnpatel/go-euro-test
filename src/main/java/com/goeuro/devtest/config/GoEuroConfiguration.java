package com.goeuro.devtest.config;

import com.goeuro.devtest.service.CsvGeneratorService;
import com.goeuro.devtest.service.CsvGeneratorServiceImpl;
import com.goeuro.devtest.service.DownloadPositionsFromJsonAPIService;
import com.goeuro.devtest.service.DownloadPositionsFromJsonAPIServiceImpl;
import com.goeuro.devtest.service.HttpURLConnectionService;
import com.goeuro.devtest.service.HttpURLConnectionServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:application.properties")
public class GoEuroConfiguration {

    @Value("${com.goeuro.devtest.json-api}")
    private String jsonAPI;

    @Value("${com.goeuro.devtest.csv-flush-size}")
    private Integer csvFlushSize;

    @Value("${com.goeuro.devtest.csv-output-dir}")
    private String csvOutputDir;

    @Bean(name = "jsonAPI")
    public String getJsonAPI() {
        return jsonAPI;
    }

    @Bean(name = "csvFlushSize")
    public Integer getCsvFlushSize() {
        return csvFlushSize;
    }

    @Bean(name = "csvOutputDir")
    public String getCsvOutputDir() {
        return csvOutputDir;
    }

    @Bean
    public DownloadPositionsFromJsonAPIService downloadPositionsFromJsonAPIService() {
        return new DownloadPositionsFromJsonAPIServiceImpl();
    }

    @Bean
    public HttpURLConnectionService httpURLConnectionService() {
        return new HttpURLConnectionServiceImpl();
    }

    @Bean
    public CsvGeneratorService csvGeneratorService() {
        return new CsvGeneratorServiceImpl();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
