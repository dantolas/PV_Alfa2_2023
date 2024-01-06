package com.kuta.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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

    public static boolean isDirectory(String filepath) throws SecurityException{
        try {
            return Files.isDirectory(Path.of(filepath));
        } catch (SecurityException e) {
            throw e;
        }
    }

    public static boolean isFile(String filepath) throws SecurityException{
        try {
            return Files.isRegularFile(Path.of(filepath));    
        } catch (SecurityException e) {
            throw e;
        }
        
    }

    public static void OverWriteFile(String text, String filepath) throws IOException{
        writer = new BufferedWriter(new FileWriter(filepath));
        writer.write(text);
        writer.close();
    }

    public static void AppendWriteFile(String text, String filepath) throws IOException{
        writer = new BufferedWriter(new FileWriter(filepath,true));
        writer.write(text);
        writer.close();
    }
    
    public static void CreateFile(String filepath) throws IOException,SecurityException,java.nio.file.FileAlreadyExistsException{
        Path newFilePath = Path.of(filepath);
        Files.createFile(newFilePath);
    }
}
