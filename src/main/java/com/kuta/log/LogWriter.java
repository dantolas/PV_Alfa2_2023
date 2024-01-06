package com.kuta.log;

import java.io.IOException;
import java.io.StringWriter;
import java.io.PrintWriter;

import com.google.gson.Gson;
import com.kuta.config.Config;
import com.kuta.errorhandling.LogWriterInitException;
import com.kuta.io.IOWorker;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class LogWriter {

    private static final String ERROR_FILE_NAME = "errorLog.json";
    private static String errorLogPath;
    private static String operationLogPath;
    private static final Gson gson = new Gson();

    public static void writeTestErrorLog(Exception e) throws SecurityException, IOException{
        IOWorker.CreateFile(errorLogPath+ERROR_FILE_NAME);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String systemTime = dtf.format(LocalDateTime.now());
        ErrorLog er = new ErrorLog("1",systemTime,e.getClass().getSimpleName(),e.getMessage(),StackTraceToString(e));
        System.out.println(er);
        System.out.println(errorLogPath+ERROR_FILE_NAME);

        IOWorker.OverWriteFile(gson.toJson(er),errorLogPath+ERROR_FILE_NAME);
    }

    public static void writeErrorLog(Exception e) throws SecurityException, IOException{
        String filePath = errorLogPath +ERROR_FILE_NAME;
        if(!IOWorker.isFile(filePath)){
            IOWorker.CreateFile(filePath);
        } 

        String fileText = IOWorker.readFileIntoString(filePath);


    }

    public static void writeOperationLog(){

    }

    public static void Init(String errorLogPath,String operationLogPath) throws LogWriterInitException,SecurityException{
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

    }

    private static String StackTraceToString(Exception e){
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    
}
