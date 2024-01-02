package com.kuta.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class IOWorker {

    private static BufferedReader reader;
    private static BufferedWriter writer;

    public static boolean isDirectory(String filepath){
        return Files.isDirectory(Path.of(filepath));
    }

    public static boolean isFile(String filepath){
        return Files.isRegularFile(Path.of(filepath));
    }
    
}
