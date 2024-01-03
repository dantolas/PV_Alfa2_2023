package com.kuta.log;

import com.kuta.config.Config;
import com.kuta.errorhandling.LogWriterInitException;
import com.kuta.io.IOWorker;

public class LogWriter {

    private static String errorLogPath;
    private static String operationLogPath;

    public static void writeErrorLog(Exception e){
        
    }

    public static void writeOperationLog(){

    }

    public static void Init(String errorLogPath,String operationLogPath) throws LogWriterInitException{
        if(!IOWorker.isDirectory(errorLogPath))
         throw new LogWriterInitException("Poskytnuta cesta pro umisteni error logu nekonci adresarem. :"+errorLogPath);

        if(!IOWorker.isDirectory(operationLogPath))
        throw new LogWriterInitException("Poskytnuta cesta pro umisteni operacniho logu nekonci adresarem. :"+operationLogPath);


    }

    
}
