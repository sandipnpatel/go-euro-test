package com.goeuro.devtest;

import com.goeuro.devtest.config.GoEuroConfiguration;
import org.apache.commons.io.FileUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.io.IOException;

/**
 * Provides ApplicationContext and test utility methods.
 */
public abstract class AbstractUnitTest {

    protected ApplicationContext ctx;

    public AbstractUnitTest() {
        ctx = new AnnotationConfigApplicationContext(GoEuroConfiguration.class);
    }


    protected void removeFiles(File dir) throws IOException {
        FileUtils.deleteDirectory(dir);
    }

    // Return content of the first file from specified directory.
    protected String getFirstFileContent(File dir) throws IOException {

        File[] files  = dir.listFiles();

        if ( files == null || files.length == 0 ) {
            return "";
        }

        for(File file : files) {
            if(file.isFile()) {
                return FileUtils.readFileToString(file);
            }
        }
        return "";
    }

}
