package com.kuta.log;

import java.io.IOException;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.nio.file.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kuta.config.Config;
import com.kuta.errorhandling.LogWriterInitException;
import com.kuta.io.IOWorker;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class LogWriter {

    private static final String ERROR_FILE_NAME = "errorLog.json";
    private static final String OPERATION_FILE_NAME = "operationLog.json";
    private static String errorLogPath;
    private static String operationLogPath;
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void writeTestErrorLog(Exception e) throws SecurityException, IOException{

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String systemTime = dtf.format(LocalDateTime.now());
        ErrorLog er = new ErrorLog("1",systemTime,e.getClass().getSimpleName(),e.getMessage(),StackTraceToString(e));
        System.out.println(er);
        System.out.println(errorLogPath+ERROR_FILE_NAME);

        IOWorker.OverWriteFile(gson.toJson(er),errorLogPath+ERROR_FILE_NAME);
    }

    private static ErrorLog[] jsonToErrorLogArray(String json){

        ErrorLog[] errorLogs = gson.fromJson(json,ErrorLog[].class);
        return errorLogs;
    }



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

    public static void Init(String errorLogPath,String operationLogPath) throws LogWriterInitException,SecurityException{
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

    

    private static String StackTraceToString(Exception e){
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    
}
