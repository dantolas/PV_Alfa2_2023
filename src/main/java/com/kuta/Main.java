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

public class Main {

    public static void main(String[] args) {
        ErrorHandler handler = new ErrorHandler();

        try {
            System.out.println(System.getProperty("os.name"));
            System.out.println(System.getProperty("user.dir"));
            System.out.println(STARTUP_MESSAGE);
            Scanner console = new Scanner(System.in);
            Config config = Config.initFromJsonFile("/home/charming/Projects/code/pv/java/alfa2/config/config.json");
            LogWriter.Init(config.GET_ERROR_LOG_DIRECTORY(), config.GET_OPERATION_LOG_DIRECTORY());
            Compressor compressor = new Compressor();
            
            while(true){
            while (true) {
                System.out.print(INPUT_POINTER);
                String input = console.nextLine();
                
                try {
                    
                } catch (Exception e) {
                    System.out.println("Invalid input.");
                }
            }
            }

        }
        catch(SecurityException e){
            handler.HandleError(e);
        }
        catch(LogWriterInitException e){
            handler.HandleError(e);
        }
        catch(ConfigInitException e){
            handler.HandleError(e);
        }
        // catch(FileNotFoundException e){
        //     handler.HandleError(e);
        // }
        // catch(IOException e){
        //     handler.HandleError(e);
        // }
        catch(PatternSyntaxException e){
            handler.HandleError(e);
        }
        catch(Exception e){
            handler.HandleError(e);
            
        }
        finally{
            System.out.println("Jelikož nastala chyba v inicializaci, program se nyní vypne.");
            System.out.println("|ENTER| to exit.");
            System.console().readLine();
            System.exit(0);
        }

        


        
    }
    

    private static final String STARTUP_MESSAGE = "| WELCOME. TYPE help AND PRESS ENTER |";
    private static final String INPUT_POINTER = "->";
}
