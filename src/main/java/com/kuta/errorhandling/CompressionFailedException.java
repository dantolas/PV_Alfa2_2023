package com.kuta.errorhandling;

public class CompressionFailedException extends Exception{
    public CompressionFailedException(String message){
        super(message);
    }
}
