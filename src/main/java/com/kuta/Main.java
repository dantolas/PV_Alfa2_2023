package com.kuta;

import java.io.Console;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.Gson;
import com.kuta.compression.Compressor;
import com.kuta.config.Config;
import com.kuta.errorhandling.ErrorHandler;
import com.kuta.io.IOWorker;
import com.kuta.log.ErrorLog;
import com.kuta.log.LogWriter;

public class Main {
    public static void main(String[] args) {
        System.out.println(System.getProperty("os.name"));
        System.out.println(System.getProperty("user.dir"));

        
        ErrorHandler handler = new ErrorHandler();

        try {
            Config config = Config.initFromJsonFile("/home/charming/Projects/code/pv/java/alfa2/config/config.json");
            LogWriter.Init(config.GET_ERROR_LOG_DIRECTORY(), config.GET_OPERATION_LOG_DIRECTORY());
            String testText = IOWorker.readFileIntoString("/home/charming/Projects/code/pv/java/alfa2/src/main/resources/text.txt");
            Compressor compressor = new Compressor(testText);
            System.out.println(compressor.getTopFrequencies(compressor.getFrequencyMap(testText), 10));
            

        }catch(Exception e){
            handler.HandleError(e);
            System.out.println("Jelikož nastala chyba v inicializaci, program se nyní vypne.");
            System.exit(0);
        }

        System.out.println("|ENTER| to exit.");
        System.console().readLine();


        
    }
}
