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

/**
 * This class provides functionality for all necessary IO operations the program uses.
 * All of the functionality is static, no instances should be created.
 */
public class IOWorker {

    private static BufferedReader reader;
    private static BufferedWriter writer;

    /**
     * Reads all contents of a file and returns as String
     * @param filepath - Path leading to file.
     * @return - String of the file contents.
     * @throws FileNotFoundException
     * @throws IOException
     */
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

    /**
     * Checks if the path leads to a directory or not.
     * @param filepath
     * @return - True if path leads to a directory.
     * @throws SecurityException
     */
    public static boolean isDirectory(String filepath) throws SecurityException{
        try {
            return Files.isDirectory(Path.of(filepath));
        } catch (SecurityException e) {
            throw e;
        }
    }
    /**
     * Checks if a path leads to a file or not.
     * @param filepath
     * @return - True if path leads to file.
     * @throws SecurityException
     */
    public static boolean isFile(String filepath) throws SecurityException{
        try {
            return Files.isRegularFile(Path.of(filepath));    
        } catch (SecurityException e) {
            throw e;
        }
        
    }

    /**
     * Writes to a file specified by filepath, overwriting old content.
     * @param text - Text to be written to file
     * @param filepath - Path to the file
     * @throws IOException
     */
    public static void OverWriteFile(String text, String filepath) throws IOException{
        writer = new BufferedWriter(new FileWriter(filepath));
        writer.write(text);
        writer.close();
    }

    /**
     * Writes to a file, appending the text given without overwriting the contents.
     * @param text - Text to be written to file
     * @param filepath - Path to the file
     * @throws IOException
     */
    public static void AppendWriteFile(String text, String filepath) throws IOException{
        writer = new BufferedWriter(new FileWriter(filepath,true));
        writer.write(text);
        writer.close();
    }
    
    /**
     * Creates a new file based on the path provided.
     * @param filepath
     * @throws IOException
     * @throws SecurityException
     * @throws java.nio.file.FileAlreadyExistsException
     */
    public static void CreateFile(String filepath) throws IOException,SecurityException,java.nio.file.FileAlreadyExistsException{
        Path newFilePath = Path.of(filepath);
        Files.createFile(newFilePath);
    }

    /**
     * Deletes a file based on path provided.
     * @param filepath
     * @throws IOException
     */
    public static void DeleteFile(String filepath) throws IOException{
        Path newFilePath = Path.of(filepath);
        Files.delete(newFilePath);
    }

    /**
     * Get size of file in bytes
     * @param filepath
     * @return
     */
    public static double getFileSizeB(String filepath){
        File file = new File(filepath);
        return file.length();
        
    }
    /**
     * Get size of file in kilobytes
     * @param filepath
     * @return
     */
    public static double getFileSizeKB(String filepath){
        File file = new File(filepath);
        return file.length() / 1_024;
    }
    /**
     * Get size of file in megabytes
     * @param filepath
     * @return
     */
    public static double getFileSizeMB(String filepath){
        File file = new File(filepath);
        return file.length() / (1_024*1_024);
    }
}
