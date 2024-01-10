package com.kuta.errorhandling;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.PatternSyntaxException;
import com.kuta.log.LogWriter;

/**
 * This class provides functionality to handle all (propably) exceptions that may occur in the program.
 * 
 * All exceptions can just be passed to the HandleError method.
 */
public class ErrorHandler {

    private final String OBECNA_ERROR_ZPRAVA = "Prosim zkontrolujte nejnovejsi zaznam v errorLogu. ID:";

    /**
     * Handles general Exception.
     * @param e
     */
    public void HandleError(Exception e){
        System.out.println("Nastala generalni chyba. Zprava: "+e.getMessage());
        try {
            String newLogId = LogWriter.writeErrorLog(e);
            System.out.println(OBECNA_ERROR_ZPRAVA + newLogId);
        } catch (IOException e1) {
            System.out.println("Nepodařilo se napsat log.");
        }
        
    }

    /**
     * Handles FileNotFoundException
     * @param e
     */
    public void HandleError(FileNotFoundException e){
        System.out.println("Nastala chyba pri hledani souboru. Zprava: "+e.getMessage());
        try {
            String newLogId = LogWriter.writeErrorLog(e);
            System.out.println(OBECNA_ERROR_ZPRAVA + newLogId);
        } catch (IOException e1) {
            System.out.println("Nepodařilo se napsat log.");
        }
    }

    /**
     * Handles IOException
     * @param e
     */
    public void HandleError(IOException e){
        System.out.println("Nastala chyba v praci s I/O systemem. Zprava:"+e.getMessage());
        try {
            String newLogId = LogWriter.writeErrorLog(e);
            System.out.println(OBECNA_ERROR_ZPRAVA + newLogId);
        } catch (IOException e1) {
            System.out.println("Nepodařilo se napsat log.");
        }
    }
    public void HandleError(CompressionFailedException e){
        System.out.println("Nastala chyba v kompresi. Zprava:"+e.getMessage());
        try {
            String newLogId = LogWriter.writeErrorLog(e);
            LogWriter.writeOperationLog(newLogId);
            System.out.println(OBECNA_ERROR_ZPRAVA + newLogId);
        } catch (IOException e1) {
            System.out.println("Nepodařilo se napsat log.");
        }
    }
    /**
     * Handles ConfigInitException
     * @param e
     */
    public void HandleError(ConfigInitException e){
        System.out.println("Nastala chyba v inicializaci konfigurace. Zprava:"+e.getMessage());
        try {
            String newLogId = LogWriter.writeErrorLog(e);
            System.out.println(OBECNA_ERROR_ZPRAVA + newLogId);
        } catch (IOException e1) {
            System.out.println("Nepodařilo se napsat log.");
        }
    }
    /**
     * Handles LogWriterInitException
     * @param e
     */
    public void HandleError(LogWriterInitException e){
        System.out.println("Nastala chyba v inicializaci zapisovace logu. Zprava:"+e.getMessage());
        try {
            String newLogId = LogWriter.writeErrorLog(e);
            System.out.println(OBECNA_ERROR_ZPRAVA + newLogId);
        } catch (IOException e1) {
            System.out.println("Nepodařilo se napsat log.");
        }
    }

    public void HandleError(SecurityException e){
        System.out.println("Nastala chyba s pristupem k souboru nebo adresari. Zprava:"+e.getMessage());
        try {
            String newLogId = LogWriter.writeErrorLog(e);
            System.out.println(OBECNA_ERROR_ZPRAVA + newLogId);
        } catch (IOException e1) {
            System.out.println("Nepodařilo se napsat log.");
        }
    }
    public void HandleError(PatternSyntaxException e){
        System.out.println("Nastala chyba s kompilaci regex vzoru. Zprava:"+e.getMessage());
        try {
            String newLogId = LogWriter.writeErrorLog(e);
            System.out.println(OBECNA_ERROR_ZPRAVA + newLogId);
        } catch (IOException e1) {
            System.out.println("Nepodařilo se napsat log.");
        }
    }

}
