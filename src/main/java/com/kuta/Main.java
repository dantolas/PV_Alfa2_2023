package com.kuta;

import java.io.Console;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.Gson;
import com.kuta.config.Config;
import com.kuta.errorhandling.ErrorHandler;
import com.kuta.io.IOWorker;
import com.kuta.log.ErrorLog;
import com.kuta.log.LogWriter;

public class Main {
    public static void main(String[] args) {
        System.out.println(System.getProperty("os.name"));
        System.out.println(System.getProperty("user.dir"));

        try {
            
            LogWriter.writeTestErrorLog(new Exception("TestException"));
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }

        ErrorHandler handler = new ErrorHandler();

        try {

            Config config = Config.initFromJsonFile("/home/charming/Projects/code/pv/java/alfa2/config/config.json");

            System.out.println(config);

        } catch(FileNotFoundException e){
            handler.HandleError(e);

        } catch(IOException e){
            handler.HandleError(e);
        }catch(Exception e){
            handler.HandleError(e);
        }

        System.out.println("ENTER to exit.");
        System.console().readLine();


        
    }
}
