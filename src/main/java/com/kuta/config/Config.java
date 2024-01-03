package com.kuta.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.Streams;
import com.kuta.errorhandling.ConfigInitException;
import com.kuta.io.IOWorker;

public class Config {

    @SerializedName("cesta_k_souboru")
    private String PATH_TO_INPUT;

    @SerializedName("adresar_umisteni_outputu")
    private String OUTPUT_DIRECTORY;

    @SerializedName("nazev_output_souboru")
    private String OUTPUT_FILENAME;

    @SerializedName("adresar_umisteni_error_logu")
    private String ERROR_LOG_DIRECTORY;

    @SerializedName("adresar_umisteni_operacniho_logu")
    private String OPERATION_LOG_DIRECTORY;

    public static final String[] DEFAULT_PATHS = {
        System.getProperty("User.dir")+"/src/main/resources/text.txt",
        System.getProperty("User.dir")+"/output/",
        "Compressed.txt",
        System.getProperty("User.dir")+"/log/"
    };


    public Config(String PATH_TO_INPUT, String OUTPUT_DIRECTORY, String OUTPUT_FILENAME, String ERROR_LOG_DIRECTORY, String OPERATION_LOG_DIRECTORY){
        this.PATH_TO_INPUT = PATH_TO_INPUT;
        this.OUTPUT_DIRECTORY = OUTPUT_DIRECTORY;
        this.OUTPUT_FILENAME = OUTPUT_FILENAME;
        this.ERROR_LOG_DIRECTORY = ERROR_LOG_DIRECTORY;
        this.OPERATION_LOG_DIRECTORY = OPERATION_LOG_DIRECTORY;

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
    

    private boolean configInitialized(){

        return this.OUTPUT_DIRECTORY != null && this.PATH_TO_INPUT != null && this.OUTPUT_FILENAME != null;
    }

    private void checkDefaults(){

     
        if(this.PATH_TO_INPUT.toLowerCase().equals("default")){
            this.PATH_TO_INPUT = Config.DEFAULT_PATHS[0];
        }

        if(this.OUTPUT_DIRECTORY.toLowerCase().equals("default")){
            this.OUTPUT_DIRECTORY = Config.DEFAULT_PATHS[1];
        }

        if(this.OUTPUT_FILENAME.toLowerCase().equals("default")){
            this.OUTPUT_FILENAME = Config.DEFAULT_PATHS[2];
        }

        if(this.ERROR_LOG_DIRECTORY.toLowerCase().equals("default")){
            this.ERROR_LOG_DIRECTORY = Config.DEFAULT_PATHS[3];
        }

        if(this.OPERATION_LOG_DIRECTORY.toLowerCase().equals("default")){
            this.OPERATION_LOG_DIRECTORY = Config.DEFAULT_PATHS[3];
        }
    }
    

    public static Config initFromJsonFile(String filepath) throws FileNotFoundException,IOException,ConfigInitException{

        if(!IOWorker.isFile(filepath)) throw new FileNotFoundException("Config soubor nebyl nalezen");

        String jsonString = IOWorker.readFileIntoString(filepath);

        Gson gson = new Gson();

        Config config = gson.fromJson(jsonString.toString(), Config.class);
        
        if(!config.configInitialized()
        ) throw new ConfigInitException("Jeden z atributu nebyl inicializovan. Zkontrolujte ze je config soubor ve spravnem formatu.");

        config.checkDefaults();




        return config;
    }
}
