package com.kuta;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.kuta.io.IOWorker;

public class TestIOWorker {
    private final String WORKING_DIRECTORY = System.getProperty("user.dir");

    @Test
    public void testFileNotFound(){
        String INCORRECT_PATH = "ABCDEabcde.abcde";
        assertAll(
                "File not found ",
                () -> assertThrows(FileNotFoundException.class,()->{
                    IOWorker.readFileIntoString(INCORRECT_PATH);
                }));
    }

    @Test
    public void testIsDirectory(){
        assertEquals(true, IOWorker.isDirectory(WORKING_DIRECTORY));
    }

    @Test 
    public void testIsFile(){
        assertEquals(true, IOWorker.isFile(WORKING_DIRECTORY+"/config/config.json"));
    }

    @Test
    public void testWriteToFile(){
        String textToWrite = WORKING_DIRECTORY + WORKING_DIRECTORY;
        String filepath = WORKING_DIRECTORY+"src/main/resources/unitTestFile.txt";
        try {
            IOWorker.OverWriteFile(textToWrite, filepath);
        } catch (IOException e) {
        }
        try {
            assertEquals(textToWrite, IOWorker.readFileIntoString(filepath));
        } catch (FileNotFoundException e) {
            
        } catch (IOException e) {
            
        }
    }

}
