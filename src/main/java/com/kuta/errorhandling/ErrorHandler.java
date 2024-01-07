package com.kuta.errorhandling;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.kuta.log.LogWriter;

public class ErrorHandler {

    private final String OBECNA_ERROR_ZPRAVA = "Prosim zkontrolujte nejnovejsi zaznam v errorLogu. ID:";

   
    public void HandleError(Exception e){
        System.out.println("Nastala generalni chyba. Zprava: "+e.getMessage());
        try {
            String newLogId = LogWriter.writeErrorLog(e);
            System.out.println(OBECNA_ERROR_ZPRAVA + newLogId);
        } catch (IOException e1) {
            System.out.println("Nepodařilo se napsat log.");
        }
        
        e.printStackTrace();
    }

    public void HandleError(FileNotFoundException e){
        System.out.println("Nastala chyba pri hledani souboru. Zprava: "+e.getMessage());
        try {
            String newLogId = LogWriter.writeErrorLog(e);
            System.out.println(OBECNA_ERROR_ZPRAVA + newLogId);
        } catch (IOException e1) {
            System.out.println("Nepodařilo se napsat log.");
        }
        e.printStackTrace();
    }

    public void HandleError(IOException e){
        System.out.println("Nastala chyba v praci s I/O systemem. Zprava:"+e.getMessage());
        try {
            String newLogId = LogWriter.writeErrorLog(e);
            System.out.println(OBECNA_ERROR_ZPRAVA + newLogId);
        } catch (IOException e1) {
            System.out.println("Nepodařilo se napsat log.");
        }
        e.printStackTrace();
    }

    public void HandleError(ConfigInitException e){
        System.out.println("Nastala chyba v inicializaci konfigurace. Zprava:"+e.getMessage());
        try {
            String newLogId = LogWriter.writeErrorLog(e);
            System.out.println(OBECNA_ERROR_ZPRAVA + newLogId);
        } catch (IOException e1) {
            System.out.println("Nepodařilo se napsat log.");
        }
        e.printStackTrace();
    }

    public void HandleError(LogWriterInitException e){
        System.out.println("Nastala chyba v inicializaci zapisovace logu. Zprava:"+e.getMessage());
        try {
            String newLogId = LogWriter.writeErrorLog(e);
            System.out.println(OBECNA_ERROR_ZPRAVA + newLogId);
        } catch (IOException e1) {
            System.out.println("Nepodařilo se napsat log.");
        }
        e.printStackTrace();
    }

    public void HandleError(SecurityException e){
        System.out.println("Nastala chyba s pristupem k souboru nebo adresari. Zprava:"+e.getMessage());
        try {
            String newLogId = LogWriter.writeErrorLog(e);
            System.out.println(OBECNA_ERROR_ZPRAVA + newLogId);
        } catch (IOException e1) {
            System.out.println("Nepodařilo se napsat log.");
        }
        e.printStackTrace();
    }
}
