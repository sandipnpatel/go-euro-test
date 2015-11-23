package com.goeuro.devtest.app;

import com.goeuro.devtest.config.GoEuroConfiguration;
import com.goeuro.devtest.service.DownloadPositionsFromJsonAPIServiceImpl;
import com.google.common.base.Strings;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class GoEuroTest {

    public static void main(String[] args) {

        String city;
        String csvFileLocation;

        if ( args.length > 0 ) {
            city = args[0];
            csvFileLocation = ( args.length > 1 && !Strings.isNullOrEmpty(args[1]) ) ? args[1] : null;
        } else {
            throw new IllegalArgumentException(String.format("City Name must be provided as first argument."));
        }

        ApplicationContext ctx = new AnnotationConfigApplicationContext(GoEuroConfiguration.class);
        DownloadPositionsFromJsonAPIServiceImpl service = ctx.getBean(DownloadPositionsFromJsonAPIServiceImpl.class);

        service.downloadPositions(city, csvFileLocation);
    }
}
