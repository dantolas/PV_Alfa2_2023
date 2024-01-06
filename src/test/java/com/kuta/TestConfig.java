package com.kuta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;
import com.kuta.config.Config;

public class TestConfig {
    

    @Test
    public void testInitFileNotFound() {

        assertThrows(FileNotFoundException.class, () ->{
            Config.initFromJsonFile("Nonnexistent path");
        });
    }

}
