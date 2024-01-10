package com.kuta;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.kuta.io.IOWorker;
import com.kuta.log.LogWriter;

public class TestLogWriter {
    private final String WORKING_DIRECTORY = System.getProperty("user.dir");
    
    @Test
    public void testErrorLogWritten(){
    }

    @Test
    public void testOperationLogWritten(){

    }
}
