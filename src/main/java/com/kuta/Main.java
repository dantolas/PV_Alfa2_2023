package com.kuta;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.kuta.errorhandling.ErrorHandler;

public class Main {
    public static void main(String[] args) {
        System.out.println(System.getProperty("os.name"));
        System.out.println(System.getProperty("user.dir"));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        System.out.println(dtf.format(LocalDateTime.now()));
        ErrorHandler handler = new ErrorHandler();

        try {
            
        Config config = Config.initFromJsonFile("/home/charming/Projects/code/pv/java/alfa2/config/config.json");


        System.out.println(config.GET_OUTPUT_DIRECTORY() + config.GET_OUTPUT_FILENAME() + config.GET_PATH_TO_INPUT());

        } catch(FileNotFoundException e){
            handler.HandleError(e);

        } catch(IOException e){
            e.printStackTrace();
        }

        
    }
}
