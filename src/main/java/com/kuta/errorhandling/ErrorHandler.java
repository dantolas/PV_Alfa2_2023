package com.kuta.errorhandling;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ErrorHandler {
   
   
    public void HandleError(Exception e){
        e.printStackTrace();
    }

    public void HandleError(FileNotFoundException e){

    }

    public void HandleError(IOException e){

    }
}
