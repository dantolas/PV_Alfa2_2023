package com.kuta.ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import com.kuta.compression.Compressor;
import com.kuta.config.Config;
import com.kuta.errorhandling.CompressionFailedException;
import com.kuta.errorhandling.ErrorHandler;
import com.kuta.io.IOWorker;
import com.kuta.log.ErrorLog;
import com.kuta.log.LogWriter;
import com.kuta.log.OperationLog;

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
                handleInput(input,scanner);
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
    private void handleInput(String input,Scanner scanner) throws FileNotFoundException, IOException, CompressionFailedException{
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
                 
                logLookup(input,scanner);
                break;
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

    private boolean stringStartsWith(String string, String prefix){
        return string.toLowerCase().startsWith(prefix);
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

    private void logLookup(String input,Scanner scanner) throws FileNotFoundException, IOException{
        while (true) {
                System.out.println("(O) -> Operacni log\n(E) - Error log\n   exit -> Zpatky.");
                System.out.print(INPUT_POINTER);
                input = scanner.nextLine();
                if(input == null) continue;
                if(input.toLowerCase().equals("exit")) return;

                if(stringStartsWith(input, "o")){
                    OperationLog log = operLogLookup(input, scanner);
                    if (log == null) {
                        System.out.println("Log nebyl nalezen.");
                        return;
                    }
                    System.out.println(log);
                }

                if(stringStartsWith(input, "e")){
                    ErrorLog log = errorLogLookup(input, scanner);
                    if(log == null){
                        System.out.println("Log nebyl nalezen.");
                        return;
                    } 
                    System.out.println(log);
                    
                }

        }
        
    }

    private OperationLog operLogLookup(String input, Scanner scanner) throws FileNotFoundException, IOException{
        while (true) {
                System.out.println("(N) -> Nejnovejsi zaznam\n(P) -> Podle casu\n   exit -> Zpatky.");
                System.out.print(INPUT_POINTER);
                input = scanner.nextLine();
                if(input == null) continue;
                if(input.toLowerCase().equals("exit")) return null;

                if(stringStartsWith(input, "p")){
                    System.out.println("Zadejte cas (format: dd.MM.yyyy_HH-mm-ss priklad:10.01.2024_08-58-58):");
                    System.out.print(INPUT_POINTER);
                    input = scanner.nextLine();
                    return getOperLogByTime(input);
                    
                }

                if(stringStartsWith(input, "n")){
                    return getLastOperLog();
                }

                
        }
    }

    private OperationLog getOperLogByTime(String time) throws FileNotFoundException, IOException{
        OperationLog[] logs= LogWriter.getOperationLogArray();
        for (OperationLog operLog : logs) {
            if(operLog.time.equals(time)) return operLog;
        }
        return null;
    }

    private OperationLog getLastOperLog() throws FileNotFoundException, IOException{
        OperationLog[] logs = LogWriter.getOperationLogArray();
        return logs[logs.length -1];
    }

    private ErrorLog errorLogLookup(String input,Scanner scanner) throws FileNotFoundException, IOException{
        while (true) {
                System.out.println("(N) -> Nejnovejsi zaznam\n(P) -> Podle casu\n(ID) -> Podle ID\n   exit -> Zpatky.");
                System.out.print(INPUT_POINTER);
                input = scanner.nextLine();
                if(input == null) continue;
                if(input.toLowerCase().equals("exit")) return null;

                if(stringStartsWith(input, "p")){
                    System.out.println("Zadejte cas (format: dd.MM.yyyy_HH-mm-ss priklad:10.01.2024_08-58-58):");
                    System.out.print(INPUT_POINTER);
                    input = scanner.nextLine();
                    return getErrorLogByTime(input);
                    
                }

                if(stringStartsWith(input, "n")){
                    return getLastErrorLog();
                }

                if(stringStartsWith(input, "id")){
                    System.out.println("Zadejte id (priklad: f69bcc98-f96e-4eb4-9299-04fe6385bcd4): ");
                    System.out.print(INPUT_POINTER);
                    input = scanner.nextLine();
                    return getErrorLogByID(input);
                }

        }
    }

    private ErrorLog getLastErrorLog() throws FileNotFoundException, IOException{
        ErrorLog[] logs= LogWriter.getErrorLogArray();
        return logs[logs.length - 1];
    }

    private ErrorLog getErrorLogByTime(String time) throws FileNotFoundException, IOException{
        ErrorLog[] logs= LogWriter.getErrorLogArray();
        for (ErrorLog errorLog : logs) {
            if(errorLog.time.equals(time)) return errorLog;
        }
        return null;
    }

    private ErrorLog getErrorLogByID(String id) throws FileNotFoundException, IOException{
        ErrorLog[] logs= LogWriter.getErrorLogArray();
        for (ErrorLog errorLog : logs) {
            if(errorLog.id.equals(id)) return errorLog;
        }
        return null;
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
