package com.kuta.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class IOWorker {

    private static BufferedReader reader;
    private static BufferedWriter writer;

    public static String readFileIntoString(String filepath) throws FileNotFoundException,IOException{
        reader = new BufferedReader(new FileReader(new File(filepath)));

        StringBuilder builder = new StringBuilder();
        String currentLine = reader.readLine();
        while (currentLine != null) {
         builder.append(currentLine);
         currentLine = reader.readLine();   
        }

        reader.close();
        return builder.toString();
    }

    public static boolean isDirectory(String filepath){
        return Files.isDirectory(Path.of(filepath));
    }

    public static boolean isFile(String filepath){
        return Files.isRegularFile(Path.of(filepath));
    }
    
}
