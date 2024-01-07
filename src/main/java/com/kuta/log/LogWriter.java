package com.kuta.log;

import java.io.IOException;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.nio.file.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
 * Paths to the directories where the logs should be have to be provided to the init method.
 */
public class LogWriter {

    private static final String ERROR_FILE_NAME = "errorLog.json";
    private static final String OPERATION_FILE_NAME = "operationLog.json";
    private static String errorLogPath;
    private static String operationLogPath;
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();



    /**
     * Writes a new entry to the error log file.
     * 
     * @param e - Exception to be written to the log
     * @return - UUID (String) of the newly created log
     * @throws IOException
     */
    public static String writeErrorLog(Exception e) throws IOException{
        String filePath = errorLogPath +ERROR_FILE_NAME;

        String logFileText = IOWorker.readFileIntoString(filePath);
        
        ArrayList<ErrorLog> logs = new ArrayList<>(Arrays.asList(jsonToErrorLogArray(logFileText)));
        ErrorLog newLog = createNewLog(e);
        logs.add(newLog);

        logFileText = gson.toJson(logs);
        IOWorker.OverWriteFile(logFileText, filePath);
        return newLog.id;
    }
    
    /**
     * Helper method to transform json String to ErrorLog array.
     * 
     * Utilizes Google Gson open library
     * @param json - Json to be transformed
     * @return - ErrorLog[]
     */
    private static ErrorLog[] jsonToErrorLogArray(String json){

        ErrorLog[] errorLogs = gson.fromJson(json,ErrorLog[].class);
        return errorLogs;
    }

    /**
     * Helper method to make and return a new ErrorLog object.
     * 
     * @param e - Exception to create the object with
     * @return - ErrorLog instance
     */
    private static ErrorLog createNewLog(Exception e){
        String id = UUID.randomUUID().toString();
        String systemTime = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(LocalDateTime.now());
        String exceptionName = e.getClass().getName();
        String exceptionMessage = e.getMessage();
        String stacktrace = StackTraceToString(e);
        ErrorLog newLog = new ErrorLog(id,systemTime,exceptionName,exceptionMessage,stacktrace);
        return newLog;

    }

    public static void writeOperationLog(){

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
    public static void Init(String errorLogPath,String operationLogPath) throws LogWriterInitException{
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
            }

            if(!IOWorker.isFile(operationLogPath+OPERATION_FILE_NAME)){
            IOWorker.CreateFile(operationLogPath+OPERATION_FILE_NAME);
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
