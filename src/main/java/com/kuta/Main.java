package com.kuta;

import java.io.Console;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.PatternSyntaxException;

import com.google.gson.Gson;
import com.kuta.compression.Compressor;
import com.kuta.config.Config;
import com.kuta.errorhandling.ConfigInitException;
import com.kuta.errorhandling.ErrorHandler;
import com.kuta.errorhandling.LogWriterInitException;
import com.kuta.io.IOWorker;
import com.kuta.log.ErrorLog;
import com.kuta.log.LogWriter;
import com.kuta.ui.UI;

public class Main {

    public static void main(String[] args) {
        ErrorHandler handler = new ErrorHandler();

        Config config = null;
        Compressor compressor = null;

        try {
            config = Config.initFromJsonFile("/home/charming/Projects/code/pv/java/alfa2/config/config.json");
            String configJson = com.kuta.vendor.Gson.gson.toJson(config);
            LogWriter.Init(config.GET_ERROR_LOG_DIRECTORY(), config.GET_OPERATION_LOG_DIRECTORY(),configJson);
            compressor = new Compressor();
        } catch (ConfigInitException | LogWriterInitException e) {
            handler.HandleError(e);
            System.out.println("Jelikož došlo v chybě při inicializaci, program se nyní vypne.");
            System.out.println("Prosím zkontroluje konfiguraci, a záznam v error logu.");
            System.out.println("|ENTER| pro exit.");
            System.console().readLine();
        } catch(Exception e){
            handler.HandleError(e);
            System.out.println("Jelikož došlo v chybě při inicializaci, program se nyní vypne.");
            System.out.println("Prosím zkontroluje konfiguraci, a záznam v error logu.");
            System.out.println("|ENTER| pro exit.");
            System.console().readLine();
        }
        UI ui = new UI("->", "|Vítejte|", config, compressor,handler);
        ui.run();
                

        


        
    }
}
