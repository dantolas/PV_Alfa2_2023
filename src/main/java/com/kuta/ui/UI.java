package com.kuta.ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import com.kuta.compression.Compressor;
import com.kuta.config.Config;
import com.kuta.errorhandling.ErrorHandler;
import com.kuta.io.IOWorker;

public class UI {
    private final String INPUT_POINTER;
    private final String STARTUP_MESSAGE;
    private final Config CONFIG;
    private final Compressor COMPRESSOR;
    private final ErrorHandler HANDLER;

    public UI(String inputPointer,String startupMessage,Config config, Compressor compressor, ErrorHandler handler){
        this.INPUT_POINTER = inputPointer;
        this.STARTUP_MESSAGE = startupMessage;
        this.CONFIG = config;
        this.COMPRESSOR = compressor;
        this.HANDLER = handler;
    }

    public void run(){
        Scanner scanner = new Scanner(System.in);
        System.out.println(System.getProperty("user.dir"));
        System.out.println("os.name");
        System.out.println(STARTUP_MESSAGE);
        System.out.println(getHelp());
        while (true) {
            try {
                System.out.print(INPUT_POINTER);
                String input = scanner.nextLine();
                handleInput(input);
            } catch (Exception e) {
                HANDLER.HandleError(e);
            }
        }
    }


    private void handleInput(String input) throws FileNotFoundException, IOException{
        switch (input) {
            case "help":
                System.out.println(getHelp()); 
                break;
            
            case "comp":
                compress();
                break;

            case "log":
                throw new IOException("Test exception from log input.");
                
        
            case "exit":
                System.out.println("|Nashledanou|");
                System.exit(0);
                break;
            default:
                System.out.println("Command unknown.");
                break;
        }
    }

    private String getHelp(){
        return 
        "============================================================"+
        "\nMůžete buď kompresovat soubor, nebo prohlížet logy."+
        "\nPřikazy:"+
        "\nhelp -> Ukáže vám list příkazů."+
        "\ncomp -> Provede kompresi. Vstupní soubor, výstup a další budou odpovídat konfiguraci."+
        "\nlog -> Můžete prohlížet zapsané záznamy."+
        "\n\nexit -> Ukončit program."+
        "\n============================================================";
    }

    private void compress() throws FileNotFoundException, IOException{
        long startTime = System.currentTimeMillis();
        System.out.println("Compression started.");
        String textToCompress = IOWorker.readFileIntoString(CONFIG.GET_PATH_TO_INPUT());
        COMPRESSOR.compressToFile(textToCompress, CONFIG.GET_OUTPUT_PATH(), CONFIG.IS_TIME_TAG());
        System.out.println("Compression finished in "+((System.currentTimeMillis()-startTime)/1000)+"s.");
        try {
            
        } catch (Exception e) {
            
        }


        
    }
}
