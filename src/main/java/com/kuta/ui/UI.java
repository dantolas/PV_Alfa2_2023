package com.kuta.ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import com.kuta.compression.Compressor;
import com.kuta.config.Config;
import com.kuta.errorhandling.CompressionFailedException;
import com.kuta.errorhandling.ErrorHandler;
import com.kuta.io.IOWorker;
import com.kuta.log.LogWriter;

/**
 * This class provides all the necessary functionality
 * to interact with the client.
 * 
 * It basically just exists so that the Main class isn't so bloated.
 */
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

    /**
     * Main code run on program start
     */
    public void run(){
        Scanner scanner = new Scanner(System.in);
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

    /**
     * Handle input by the user read from the console.
     * 
     * All input is handled by passing it to a switch, and running some code by case.
     * @param input - User input
     * @throws FileNotFoundException
     * @throws IOException
     * @throws CompressionFailedException - Special exception thrown when compression fails
     */
    private void handleInput(String input) throws FileNotFoundException, IOException, CompressionFailedException{
        switch (input) {
            case "":
                break;

            case "help":
                System.out.println(getHelp()); 
                break;
            
            case "comp":
                compress();
                break;

            case "log":
                throw new IOException("Test exception from log input.");

            case "sysinfo":
                System.out.println(getSysInfo());
                break;
                
        
            case "exit":
                System.out.println("|Nashledanou|");
                System.exit(0);
                break;
            default:
                System.out.println("Neznámý příkaz '"+input+"'");
                break;
        }
    }
    /**
     * Used when user inputs 'help'
     * Tells the user what he can input to activate program functions
     * @return
     */
    private String getHelp(){
        return 
        "============================================================"+
        "\nMůžete buď kompresovat soubor, nebo prohlížet logy."+
        "\nPřikazy:"+
        "\n help -> Ukáže vám list příkazů."+
        "\n comp -> Provede kompresi. Vstupní soubor, výstup a další budou odpovídat konfiguraci."+
        "\n log -> Můžete prohlížet zapsané záznamy."+
        "\n sysinfo -> Řekne vám odkud program běží, a jaký detekuje operační systém."+
        "\n\n   exit -> Ukončit program."+
        "\n============================================================";
    }
    /**
     * Returns the programs working directory and OS name
     * @return
     */
    private String getSysInfo(){
        return "Working directory :"+System.getProperty("user.dir")+"\n Operating System:"+System.getProperty("os.name");
    }

    /**
     * Compresses the input file, creates the output file, writes logs
     * 
     * @throws FileNotFoundException
     * @throws IOException
     * @throws CompressionFailedException
     */
    private void compress() throws FileNotFoundException, IOException, CompressionFailedException{
        long startTime = System.currentTimeMillis();
        System.out.println("| Začínáme kompresi |.");
        String textToCompress = IOWorker.readFileIntoString(CONFIG.GET_PATH_TO_INPUT());
        String output = "";
        try {
            output = COMPRESSOR.compressToFile(textToCompress, CONFIG.GET_OUTPUT_PATH(), CONFIG.IS_TIME_TAG());
        } catch (Exception e) {
            throw new CompressionFailedException("Komprese selhala. Zkontrolujte nejnovější zápisy v operačním a error logu.");
        }
        System.out.println("| Komprese proběhla za "+((System.currentTimeMillis()-startTime)/1000)+"s. |");
        System.out.println("| "+output+" |");
        LogWriter.writeOperationLog(output);
        
    }
}
