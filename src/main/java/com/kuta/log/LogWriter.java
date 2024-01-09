package com.kuta.log;

import java.io.IOException;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.nio.file.*;
import com.kuta.config.Config;
import com.kuta.errorhandling.LogWriterInitException;
import com.kuta.io.IOWorker;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

/**
 * This class writes both Operation and Error logs.
 * All of the functionality is static, so no instances need to be created.
 * !!! HOWEVER, the class needs to be initialized with the init() function.
 * Paths to the directories where the logs should be, have to be provided to the init() function.
 * 
 */
public class LogWriter {

    private static final String ERROR_FILE_NAME = "errorLog.json";
    private static final String OPERATION_FILE_NAME = "operationLog.json";
    private static String errorLogPath;
    private static String operationLogPath;
    private static String configJson;



    /**
     * Writes a new entry to the error log file.
     * It first reads and loads all the error logs
     * currently in the file to an ArrayList.
     * 
     * Then a new log is created and appended to the list.
     * 
     * The list of logs is then serialized and written back to the file.
     * 
     * @param e - Exception to be written to the log
     * @return - UUID (String) of the newly created log
     * @throws IOException
     */
    public static String writeErrorLog(Exception e) throws IOException{
        String filePath = errorLogPath +ERROR_FILE_NAME;

        String logFileText = IOWorker.readFileIntoString(filePath);
        ArrayList<ErrorLog> logs = new ArrayList<>(Arrays.asList(jsonToErrorLogArray(logFileText)));
        ErrorLog newLog = createNewErrorLog(e);
        logs.add(newLog);

        logFileText = com.kuta.vendor.Gson.gson.toJson(logs);
        IOWorker.OverWriteFile(logFileText, filePath);
        return newLog.id;
    }
    
    /**
     * Helper method to transform json String to ErrorLog array.
     * 
     * 
     * Utilizes Google Gson open library
     * @param json - Json to be transformed
     * @return - ErrorLog[]
     */
    private static ErrorLog[] jsonToErrorLogArray(String json){

        ErrorLog[] errorLogs = com.kuta.vendor.Gson.gson.fromJson(json,ErrorLog[].class);
        return errorLogs;
    }

    /**
     * Helper method to make and return a new ErrorLog object.
     * 
     * @param e - Exception to create the object with
     * @return - ErrorLog instance
     */
    private static ErrorLog createNewErrorLog(Exception e){
        String id = UUID.randomUUID().toString();
        String systemTime = DateTimeFormatter.ofPattern("dd.MM.yyyy_HH-mm-ss").format(LocalDateTime.now());
        String exceptionName = e.getClass().getName();
        String exceptionMessage = e.getMessage();
        String stacktrace = StackTraceToString(e);
        Config config = com.kuta.vendor.Gson.gson.fromJson(configJson, Config.class);
        ErrorLog newLog = new ErrorLog(id,systemTime,exceptionName,exceptionMessage,stacktrace,config);
        return newLog;

    }
/**
 * SUCESFULL OPERATION
 * Writes a new entry to the operation log file.
 * It first reads and loads all the operation logs
 * to an Arraylist.
 * 
 * Then a new log is created and added to it.
 * 
 * Then the list is serialized and written back to the file.
 * 
 * @param outputPath - Path where a compressed file was created
 * @throws IOException
 */
    public static void writeOperationLog(String outputPath) throws IOException{
        String filePath = operationLogPath +OPERATION_FILE_NAME;

        String logFileText = IOWorker.readFileIntoString(filePath);
        ArrayList<OperationLog> logs = new ArrayList<>(Arrays.asList(jsonToOperationLogArray(logFileText)));
        Config config = com.kuta.vendor.Gson.gson.fromJson(configJson,Config.class);
        OperationLog newLog = createNewOperationLog(config.GET_PATH_TO_INPUT(),outputPath);
        logs.add(newLog);

        logFileText = com.kuta.vendor.Gson.gson.toJson(logs);
        IOWorker.OverWriteFile(logFileText, filePath);

    }
/**
 * FAILED OPERATION
 * Writes a new entry to the operation log file.
 * It first reads and loads all the operation logs
 * to an Arraylist.
 * 
 * Then a new log is created and added to it.
 * 
 * Then the list is serialized and written back to the file.
 * 
 * @param outputPath - Path where a compressed file was created
 * @param errorLogId - Id of the error log created along the failed operation
 * @throws IOException
 */
    public static void writeOperationLog(String errorLogId,String outputPath) throws IOException{
        String filePath = operationLogPath +OPERATION_FILE_NAME;

        String logFileText = IOWorker.readFileIntoString(filePath);
        ArrayList<OperationLog> logs = new ArrayList<>(Arrays.asList(jsonToOperationLogArray(logFileText)));
        Config config = com.kuta.vendor.Gson.gson.fromJson(configJson,Config.class);
        OperationLog newLog = createNewOperationLog(config.GET_PATH_TO_INPUT(),outputPath,errorLogId);
        logs.add(newLog);

        logFileText = com.kuta.vendor.Gson.gson.toJson(logs);
        IOWorker.OverWriteFile(logFileText, filePath);

    }
    /**
     * Helper method to transform a json string 
     * to an operationLog[] array
     * @param json 
     * @return
     */
    private static OperationLog[] jsonToOperationLogArray(String json){
        OperationLog[] logs = com.kuta.vendor.Gson.gson.fromJson(json, OperationLog[].class);
        return logs;

    }
    /**
     * SUCCESSFULL LOG
     * Helper method to create new Operation log.
     * This is the succesfull operation log version
     * @param inputPath - Input path used during compression
     * @param outputPath - Output path used during compression
     * @return - new OperationLog instance
     */
    private static OperationLog createNewOperationLog(String inputPath,String outputPath){
        OperationLog newLog;
        
        String systemTime = DateTimeFormatter.ofPattern("dd.MM.yyyy_HH-mm-ss").format(LocalDateTime.now());
        double inputSize = IOWorker.getFileSizeKB(inputPath);
        if(inputSize <= 0){
            inputSize = IOWorker.getFileSizeB(inputPath);
            double outputSize = IOWorker.getFileSizeB(outputPath);
            outputSize = IOWorker.getFileSizeB(outputPath);
            float quotient =  (float)(100 - (( outputSize / inputSize) * 100));
            String inputFileSize = Double.toString(inputSize) + "B";
            String compressedFileSize = Double.toString(outputSize) + "B";

            String compressionPercentage = Double.toString(quotient) + "%";
            newLog = new OperationLog(systemTime, inputPath, "success", "", inputFileSize, compressedFileSize,
                    compressionPercentage);
            return newLog;
        }

        if(inputSize >= 1_000_000){
            inputSize = IOWorker.getFileSizeMB(inputPath);
            double outputSize = IOWorker.getFileSizeMB(outputPath);
            float quotient =  (float)(100 - (( outputSize / inputSize) * 100));
            String inputFileSize = Double.toString(inputSize)+"MB";
            String compressedFileSize = Double.toString(outputSize)+"MB";
            
            String compressionPercentage = Double.toString(quotient)+"%";
            newLog = new OperationLog(systemTime, inputPath, "success", "", inputFileSize, compressedFileSize,
                    compressionPercentage);
            return newLog;
        }

        double outputSize = IOWorker.getFileSizeKB(outputPath);
        float quotient = (float) (100 - ((outputSize / inputSize) * 100));
        String inputFileSize = Double.toString(inputSize)+"KB";
        String compressedFileSize = Double.toString(outputSize)+"KB";
        
        String compressionPercentage = Double.toString(quotient)+"%";
        newLog = new OperationLog(systemTime, inputPath, "success", "", inputFileSize, compressedFileSize,
                compressionPercentage);
        return newLog;

    }

    /**
     * FAILURE LOG
     * Helper method to create new Operation log.
     * This is the failed operation log version
     * 
     * @param inputPath - Input path used during compression
     * @param outputPath - Output path used during compression
     * @param errorId - Id of the error log created with the failed operation
     * @return - new OperationLog instance
     */
    private static OperationLog createNewOperationLog(String inputPath,String outputPath,String errorId){
        OperationLog newLog;
        
        String systemTime = DateTimeFormatter.ofPattern("dd.MM.yyyy_HH-mm-ss").format(LocalDateTime.now());
        String inputFileSize = "";

        newLog = new OperationLog(systemTime,inputPath,"failure","","","","");
        return newLog;
    }

    /**
     * Initializes all of the static functionality of the LogWriter.
     * This method must be ran before trying to do work with the LogWriter.
     * 
     * Sets the directories where logs should be placed, checks if the necessary files are there and if not creates them.
     * 
     * @param errorLogPath - Path leading to the directory where error logs should be.
     * @param operationLogPath - Path leading to the directory where operation logs should be.
     * @throws LogWriterInitException - If any exception occurs, this special exception is thrown. 
     */
    public static void Init(String errorLogPath,String operationLogPath,String configJson) throws LogWriterInitException{
        try {
            if(!IOWorker.isDirectory(errorLogPath))
         throw new LogWriterInitException("Poskytnuta cesta pro umisteni error logu nekonci adresarem. :"+errorLogPath);

        if(!IOWorker.isDirectory(operationLogPath))
        throw new LogWriterInitException("Poskytnuta cesta pro umisteni operacniho logu nekonci adresarem. :"+operationLogPath);

        if(!errorLogPath.endsWith("/")){
            errorLogPath += "/";
        }

        if(!operationLogPath.endsWith("/")){
            operationLogPath += "/";
        }

        LogWriter.errorLogPath = errorLogPath;
        LogWriter.operationLogPath = operationLogPath;
        checkFilesExist(errorLogPath, operationLogPath);
        LogWriter.configJson = configJson;

        } catch (SecurityException e) {
            throw new LogWriterInitException("Nastal problém s přístupem k souborům. Zkontrolujte prosím že jsou všechny nakonfigurované soubory a složky správně přístupné.");
        }
        

        
       
    }

    /**
     * Helper method to check if the default files exist in provided directories.
     * If they don't, they are created.
     * @param errorLogPath
     * @param operationLogPath
     * @throws LogWriterInitException
     */
    private static void checkFilesExist(String errorLogPath, String operationLogPath) throws LogWriterInitException{
        try {
            if(!IOWorker.isFile(errorLogPath+ERROR_FILE_NAME)){
            IOWorker.CreateFile(errorLogPath+ERROR_FILE_NAME);
            IOWorker.OverWriteFile("[]", errorLogPath+ERROR_FILE_NAME);
            }

            if(!IOWorker.isFile(operationLogPath+OPERATION_FILE_NAME)){
            IOWorker.CreateFile(operationLogPath+OPERATION_FILE_NAME);
            IOWorker.OverWriteFile("[]", operationLogPath+OPERATION_FILE_NAME);
            }
        }catch (FileAlreadyExistsException e){

        }catch (IOException e) {
            throw new LogWriterInitException("Chyba při kontrole log souborů. Zkontrolujte že soubory errorLog.json a operationLog.json jsou umístěny v adresářích pro které jsou nakonfigurované.");
        }


    }

    
    /**
     * Helper method that turns Exception.stacktrace() to a string, because it's not provided by default.
     * That's java for you :]
     * @param e
     * @return
     */
    private static String StackTraceToString(Exception e){
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    
}
