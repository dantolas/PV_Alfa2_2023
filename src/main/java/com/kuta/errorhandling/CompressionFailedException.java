package com.kuta.errorhandling;

/**
 * Custom exception used when compression of a file fails
 */
public class CompressionFailedException extends Exception{
    public CompressionFailedException(String message){
        super(message);
    }
}
