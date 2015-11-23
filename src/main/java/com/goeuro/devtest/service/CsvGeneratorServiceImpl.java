package com.goeuro.devtest.service;

import com.goeuro.devtest.json.JsonLocationInfo;
import com.goeuro.devtest.util.Util;
import com.google.common.base.Strings;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class CsvGeneratorServiceImpl implements CsvGeneratorService{

    private final static Logger logger = Logger.getLogger(CsvGeneratorServiceImpl.class);

    private static final SimpleDateFormat DATE_TIME_FORMATTER = new SimpleDateFormat("dd.MM.yyyy'T'HH.mm.ss.SSS");

    private static final String FILENAME_EXTENSION_CSV = ".csv";
    private static final String DIR_NAME_TEMP = "temp";
    private static final String FIELD_DELIMITER = ",";

    @Autowired
    private Integer csvFlushSize;

    @Autowired
    private String csvOutputDir;

    @Override
    public void generateCsv(String fineNamePrefix, String csvFileLocation, JsonLocationInfo[] jsonLocationInfoArr) {

        if (Strings.isNullOrEmpty(fineNamePrefix) ) {
            throw new IllegalArgumentException(String.format("File name prefix should not be null: '%s'", fineNamePrefix));
        }

        if (Strings.isNullOrEmpty(csvFileLocation) ) {
            throw new IllegalArgumentException(String.format("Invalid destination path: '%s'", csvFileLocation));
        }

        if(jsonLocationInfoArr == null) {
            jsonLocationInfoArr = new JsonLocationInfo[0];
        }

        Date date = new Date();

        if (Strings.isNullOrEmpty(csvFileLocation) ) {
            csvFileLocation = csvOutputDir;
        }

        // Generate csv file in temp directory.
        Path tempFilePath = createTempOutputFilePath(csvFileLocation, fineNamePrefix, date);
        generateCsv(tempFilePath, jsonLocationInfoArr);

        // Move file to actual directory.
        Path filePath = createOutputFilePath(csvFileLocation, fineNamePrefix, date);
        moveFile(tempFilePath, filePath);

        logger.info(String.format("%s file %s generated successfully.", FILENAME_EXTENSION_CSV, filePath.toFile().getAbsolutePath()));
    }

    private Path createOutputFilePath(String destPath, String fineNamePrefix, Date date) {
        return Paths.get(destPath).resolve(createFileName(fineNamePrefix, date));
    }

    private Path createTempOutputFilePath(String destPath, String fineNamePrefix, Date date) {
        return Paths.get(destPath).resolve(DIR_NAME_TEMP).resolve(createFileName(fineNamePrefix, date));
    }

    private String createFileName(String fineNamePrefix, Date date) {

        return fineNamePrefix + "_" + DATE_TIME_FORMATTER.format(date) + FILENAME_EXTENSION_CSV;
    }

    private void generateCsv(Path path, JsonLocationInfo[] jsonLocationInfoArr) {
        createParentDirIfNotExists(path);

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path.toFile()), Util.ENCODING_UTF8))) {

            addHeader(writer);

            int count = 1;
            for(JsonLocationInfo info : jsonLocationInfoArr) {

                addCsvRow(writer, info);

                count++;
                if ( count >= csvFlushSize ) {
                    writer.flush();
                    count = 0;
                }
            }

            writer.flush();

        } catch (IOException e) {
            throw new RuntimeException(String.format("Failed to write %s to file system.", FILENAME_EXTENSION_CSV), e);
        }
    }

    private void addHeader(Writer writer) throws IOException{
        writer.write("_id" + FIELD_DELIMITER +
                    "name" + FIELD_DELIMITER +
                    "type" + FIELD_DELIMITER +
                    "latitude" + FIELD_DELIMITER +
                    "longitude" + System.lineSeparator());
    }

    private void addCsvRow(Writer writer, JsonLocationInfo info) throws IOException{
        writer.write(Util.formatLong(info._id) + FIELD_DELIMITER +
                    Util.formatString(info.name) + FIELD_DELIMITER +
                    Util.formatString(info.type) + FIELD_DELIMITER +
                    (info.geo_position == null ? "" : Util.formatDouble(info.geo_position.latitude)) + FIELD_DELIMITER +
                    (info.geo_position == null ? "" : Util.formatDouble(info.geo_position.longitude)) + System.lineSeparator());
    }

    private void moveFile(Path source, Path destination) {
        try {
            Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException e) {
            throw new RuntimeException(
                    String.format("Failed to move file. Source:'%s', Destination:'%s'",source.toFile().getAbsolutePath(), destination.toFile().getAbsolutePath()),
                    e);
        }
    }

    private void createParentDirIfNotExists(Path filePath) {

        File parentDir = filePath.toFile().getParentFile();

        if (!parentDir.exists()) {

            if (!parentDir.mkdirs()) {
                throw new RuntimeException(String.format("Unable to create directory %s.", parentDir.getAbsolutePath()));
            }

        } else if (parentDir.isFile()) {
            throw new RuntimeException(String.format("%s is exists as a file.", parentDir.getAbsolutePath()));
        }
    }
}
