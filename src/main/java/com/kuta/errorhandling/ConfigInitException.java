package com.kuta.errorhandling;

/**
 * Custom exception for when the initialization of the Config class fails
 */
public class ConfigInitException extends RuntimeException{
    public ConfigInitException(String errorMessage){
        super(errorMessage);
    }
}
