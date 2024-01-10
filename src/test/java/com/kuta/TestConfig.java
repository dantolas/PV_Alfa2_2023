package com.kuta;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import com.kuta.config.Config;
import com.kuta.errorhandling.ConfigInitException;

/**
 * Tests the Config.java class and it's functionality
 */
public class TestConfig {
    private final String WORKING_DIRECTORY = System.getProperty("user.dir");
    

    @Test
    public void testInitFileNotFound() {

        assertThrows(ConfigInitException.class, () ->{
            Config.initFromJsonFile("Nonnexistent path");
        });
    }

    @Test
    public void testWrongFileExtension(){
        assertThrows(ConfigInitException.class, ()->{
           Config.initFromJsonFile(WORKING_DIRECTORY+"/src/main/resources/testConfig.json"); 
        });
    }

    @Test
    public void testInitializedCorrectly(){
        assertDoesNotThrow(()->{
            Config.initFromJsonFile(WORKING_DIRECTORY+"/config/config.json");
        });
    }

    @Test
    public void changedDefaults(){
        Config testConfig = new Config("default","default","default","default","default","default");
        testConfig.checkDefaults();
        assertNotEquals(testConfig.GET_ERROR_LOG_DIRECTORY(), "default");
        assertNotEquals(testConfig.GET_OPERATION_LOG_DIRECTORY(), "default");
        assertNotEquals(testConfig.GET_OUTPUT_DIRECTORY(), "default");
        assertNotEquals(testConfig.GET_OUTPUT_FILENAME(), "default");
        assertNotEquals(testConfig.GET_PATH_TO_INPUT(), "default");
    }

}
