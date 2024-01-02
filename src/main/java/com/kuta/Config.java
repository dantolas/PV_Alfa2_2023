package com.kuta;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.kuta.errorhandling.ConfigInitException;

public class Config {

    @SerializedName("cesta_k_souboru")
    private String PATH_TO_INPUT;

    @SerializedName("adresar_umisteni_outputu")
    private String OUTPUT_DIRECTORY;

    @SerializedName("nazev_output_souboru")
    private String OUTPUT_FILENAME;

    public Config(String PATH_TO_INPUT, String OUTPUT_DIRECTORY, String OUTPUT_FILENAME){
        this.PATH_TO_INPUT = PATH_TO_INPUT;
        this.OUTPUT_DIRECTORY = OUTPUT_DIRECTORY;
        this.OUTPUT_FILENAME = OUTPUT_FILENAME;
    }

    public Config(){

    }



    public String GET_PATH_TO_INPUT(){
        return PATH_TO_INPUT;
    }

    public String GET_OUTPUT_DIRECTORY(){
        return OUTPUT_DIRECTORY;
    }

    public String GET_OUTPUT_FILENAME(){
        return OUTPUT_FILENAME;
    }
    

    

    public static Config initFromJsonFile(String filepath) throws FileNotFoundException,IOException{

        Gson gson = new Gson();
        BufferedReader reader = new BufferedReader(new FileReader(new File(filepath)));

        StringBuilder jsonString = new StringBuilder();
        String currentLine = reader.readLine();
        while (currentLine != null) {
            jsonString.append(currentLine);
            currentLine = reader.readLine();
        }

        reader.close();

        Config config = gson.fromJson(jsonString.toString(), Config.class);
        
        if(
            config.OUTPUT_DIRECTORY == null ||
            config.PATH_TO_INPUT == null ||
            config.OUTPUT_FILENAME == null
        ) throw new ConfigInitException("Jeden z atributu nebyl inicializovan. Zkontrolujte ze je config soubor ve spravnem formatu.");


        return config;
    }
}
