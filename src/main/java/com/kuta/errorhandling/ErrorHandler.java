package com.kuta.errorhandling;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ErrorHandler {

    private final String OBECNA_ERROR_ZPRAVA = "Prosim zkontrolujte nejnovejsi zaznam v errorLogu.";

   
    public void HandleError(Exception e){
        System.out.println("Nastala generalni chyba. Zprava: "+e.getMessage());
        System.out.println(OBECNA_ERROR_ZPRAVA);
        e.printStackTrace();
    }

    public void HandleError(FileNotFoundException e){
        System.out.println("Nastala chyba pri hledani souboru. Zprava: "+e.getMessage());
        System.out.println(OBECNA_ERROR_ZPRAVA);
        e.printStackTrace();
    }

    public void HandleError(IOException e){
        System.out.println("Nastala chyba v praci s I/O systemem. Zprava:"+e.getMessage());
        System.out.println(OBECNA_ERROR_ZPRAVA);
        e.printStackTrace();
    }

    public void HandleError(ConfigInitException e){
        System.out.println("Nastala chyba v inicializaci konfigurace. Zprava:"+e.getMessage());
        System.out.println(OBECNA_ERROR_ZPRAVA);
        e.printStackTrace();
    }

    public void HandleError(SecurityException e){
        System.out.println("Nastala chyba s pristupem k souboru nebo adresari. Zprava:"+e.getMessage());
        System.out.println(OBECNA_ERROR_ZPRAVA);
    }
}
