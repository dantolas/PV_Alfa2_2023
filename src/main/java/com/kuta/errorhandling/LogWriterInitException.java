package com.kuta.errorhandling;

/**
 * Custom exception to be used when initialization of the LogWriter class fails
 */
public class LogWriterInitException extends Exception{
    public LogWriterInitException(String message){
        super(message);
    }
}
