package com.goeuro.devtest.service;

import com.goeuro.devtest.util.Util;
import com.google.common.base.Strings;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

@Service
public class HttpURLConnectionServiceImpl implements HttpURLConnectionService{

    private final static Logger logger = Logger.getLogger(HttpURLConnectionServiceImpl.class);

    private static final String USER_AGENT = "Mozilla/5.0";

    @Override
    public String sendGet(String url) {

        if (Strings.isNullOrEmpty(url) ) {
            throw new IllegalArgumentException(String.format("Invalid Json API: '%s'", url));
        }

        HttpURLConnection con = null;
        BufferedReader in = null;

        try {
            URL obj = new URL(url);
            con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);

            int responseCode = con.getResponseCode();
            logger.info(String.format("Sending 'GET' request to URL : '%s'", url));
            logger.info(String.format("Response Code : '%s'", responseCode));

            in = new BufferedReader(new InputStreamReader(con.getInputStream(), Charset.forName(Util.ENCODING_UTF8)));

            StringBuilder response = new StringBuilder();

            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }

            return response.toString();

        } catch(IOException e) {
            logger.error(String.format("Unable to get data from JSON API '%s'. Exception: %s", url, e.getMessage()));
            throw new RuntimeException(e);
        } finally {
            try {
                if ( con != null ) {
                    con.disconnect();
                }
                if ( in != null ) {
                    in.close();
                }
            } catch (IOException e) {
                logger.error("Unable to close input connections:" + e.getMessage());
                throw new RuntimeException(e);
            }

        }

    }
}
