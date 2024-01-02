package com.kuta.errorhandling;

public class ConfigInitException extends RuntimeException{
    public ConfigInitException(String errorMessage){
        super(errorMessage);
    }
}
