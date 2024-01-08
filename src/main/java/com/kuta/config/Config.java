package com.kuta.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.kuta.errorhandling.ConfigInitException;
import com.kuta.io.IOWorker;

/**
 * This class is a deserialized version of config/config.json file.
 * 
 * It provides all configuration to other parts of the program. 
 */
public class Config {

    public static transient final String DEFAULT_CONFIG_PATH = System.getProperty("user.dir")+"";

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
        System.getProperty("user.dir")+"/src/main/resources/testText.txt",
        System.getProperty("user.dir")+"/output/",
        "Compressed.txt",
        System.getProperty("user.dir")+"/log/"
    };

    /**
     * Manual Constructor
     * 
     * @param PATH_TO_INPUT
     * @param OUTPUT_DIRECTORY
     * @param OUTPUT_FILENAME
     * @param ERROR_LOG_DIRECTORY
     * @param OPERATION_LOG_DIRECTORY
     */
    public Config(String PATH_TO_INPUT, String OUTPUT_DIRECTORY, String OUTPUT_FILENAME, String ERROR_LOG_DIRECTORY, String OPERATION_LOG_DIRECTORY){
        this.PATH_TO_INPUT = PATH_TO_INPUT;
        this.OUTPUT_DIRECTORY = OUTPUT_DIRECTORY;
        this.OUTPUT_FILENAME = OUTPUT_FILENAME;
        this.ERROR_LOG_DIRECTORY = ERROR_LOG_DIRECTORY;
        this.OPERATION_LOG_DIRECTORY = OPERATION_LOG_DIRECTORY;

    }
    /**
     * Default constructor
     */
    public Config(){

    }



    @Override
    public String toString() {
        return "Config [PATH_TO_INPUT=" + PATH_TO_INPUT + ", OUTPUT_DIRECTORY=" + OUTPUT_DIRECTORY
                + ", OUTPUT_FILENAME=" + OUTPUT_FILENAME + ", ERROR_LOG_DIRECTORY=" + ERROR_LOG_DIRECTORY
                + ", OPERATION_LOG_DIRECTORY=" + OPERATION_LOG_DIRECTORY + "]";
    }

    /**
     * 
     * @return - Path to input file.
     */
    public String GET_PATH_TO_INPUT(){
        return PATH_TO_INPUT;
    }

    /**
     * 
     * @return - Path to output directory.
     */
    public String GET_OUTPUT_DIRECTORY(){
        return OUTPUT_DIRECTORY;
    }

    /**
     * 
     * @return - File name of compressed output file.
     */
    public String GET_OUTPUT_FILENAME(){
        return OUTPUT_FILENAME;
    }
    
    /**
     * 
     * @return - Directory where error log should reside.
     */
    public String GET_ERROR_LOG_DIRECTORY(){
        return ERROR_LOG_DIRECTORY;
    }

    /**
     * 
     * @return - Directory where operation log should reside.
     */
    public String GET_OPERATION_LOG_DIRECTORY(){
        return OPERATION_LOG_DIRECTORY;
    }

    /**
     * Helper method to check if all attributes have been initialized.
     * Not very pretty, but iterating through class attributes looks even worse.
     * @return - True if all attributes have been initialized
     */
    private boolean configInitialized(){

        return this.OUTPUT_DIRECTORY != null && this.PATH_TO_INPUT != null && this.OUTPUT_FILENAME != null && this.ERROR_LOG_DIRECTORY != null && this.OPERATION_LOG_DIRECTORY != null;
    }

    /**
     * Helper method to check what attributes should be set to the default value.
     * 
     * It only changes to default paths if the value in config file is exactly (case insensitive) 'default'
     */
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
    
    /**
     * Helper method to check if all paths provided are valid.
     * @throws SecurityException - Exception thrown when access to file is denied.
     */
    private void checkPathsValidity() throws SecurityException{
        if(!IOWorker.isDirectory(ERROR_LOG_DIRECTORY)) 
        throw new ConfigInitException("Cesta k umisteni error logu nekonci adresarem. : "+ERROR_LOG_DIRECTORY);

        if(!IOWorker.isFile(PATH_TO_INPUT))
        throw new ConfigInitException("Cesta k input souboru nekonci souborem. :"+PATH_TO_INPUT);

        if(!IOWorker.isDirectory(OUTPUT_DIRECTORY))
        throw new ConfigInitException("Cesta k output adresari nekonci adresarem. :"+OUTPUT_DIRECTORY);

        if(!IOWorker.isDirectory(OPERATION_LOG_DIRECTORY))
        throw new ConfigInitException("Cesta k umisteni operacniho logu nekonci adresarem. :"+OPERATION_LOG_DIRECTORY);
    }

    /**
     * Creates and returns a Config instance from a json file.
     * @param filepath - Path to config.json file
     * @return - Fully prepared Config instance
     * @throws ConfigInitException - Special exception thrown when any part of initialization fails.
     */
    public static Config initFromJsonFile(String filepath) throws ConfigInitException{

        try {
            if(!IOWorker.isFile(filepath)) throw new FileNotFoundException("Config soubor nebyl nalezen a mozna chybi.");

        String jsonString = IOWorker.readFileIntoString(filepath);

        Gson gson = new Gson();

        Config config = gson.fromJson(jsonString.toString(), Config.class);
        
        if(!config.configInitialized()
        ) throw new ConfigInitException("Jeden z atributu nebyl inicializovan. Zkontrolujte ze je config soubor ve spravnem formatu podle dokumentace.");

        config.checkDefaults();

        config.checkPathsValidity();

        return config;
        } catch (Exception e) {
            throw new ConfigInitException("Nastala chyba pri inicializaci konfigurace. :"+e.getMessage());
        }
        
    }
}
