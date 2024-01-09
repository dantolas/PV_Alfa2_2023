package com.kuta.compression;

import java.util.HashMap;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.regex.PatternSyntaxException;

import com.kuta.io.IOWorker;

/**
 * Class that provides functionality to compress text files.
 * 
 * 
 */
public class Compressor {
    
    public final String REGEX_TO_SPLIT_WORDS = "([,.:;]*\s+|[,.:;]+\s*)";
    public final String REGEX_VOWELS = "[aeiouyAEIOUYáéíóůúýÁÉÍÓÚŮÝ]";
    
    public Compressor(){

    }
    /**
     * Compresses given text to a new text file.
     * 
     * New compressed file name may be tagged with current time.
     * @param text - Text to be compressed
     * @param outputPath - Output compressed file location
     * @param TIME_TAG - Tag the new file name with system time
     * @return - Path to the newly created file
     * @throws IOException
     */
    public String compressToFile(String text,String outputPath,boolean TIME_TAG) throws IOException{
        if(TIME_TAG){
            outputPath = addTimeTagToFilePath(outputPath);
        }

        

        String compressedText = getCompressedText(text);
        IOWorker.CreateFile(outputPath);
        IOWorker.OverWriteFile(compressedText, outputPath);
        return outputPath;
    }

    /**
     * Helper method to add system time to a String
     * @param string - string to append time to
     * @return - new String with the time appended
     */
    private String addTimeTagToFilePath(String string){
        String[] split = string.split("\\.");
        StringBuilder builder = new StringBuilder(split[0]);
        builder.append("_");
        builder.append(DateTimeFormatter.ofPattern("dd.MM.yyyy_HH-mm-ss").format(LocalDateTime.now()));
        builder.append(".");
        for(int i = 1; i < split.length;i++){
            builder.append(split[i]);
        }
        return builder.toString();
        
    }

    /**
     * Returns compressed text (string). 
     * Compression method used is based on different criterias.
     * @param text - Text to be compressed
     * @return
     * @throws PatternSyntaxException
     */
    public String getCompressedText(String text) throws PatternSyntaxException{
        
        String[] words = getWords(text);
        int wordCount = getWordCount(text);
       
        if(wordCount > 100) {
           text = compressTextShortcuts(text); 
        }

        if(wordCount < 100){
            text = compressTextVowels(text);
        }

        return text;
        
    }

    /**
     * Compresses given text by removing the first vowel of each word
     * @param text
     * @return
     * @throws PatternSyntaxException
     */
    public String compressTextVowels(String text) throws PatternSyntaxException{
        String[] words= getWords(text);
        String[] wordsCopy = getWords(text);

        for (int i = 0; i < words.length; i++) {
            if(words[i].length() < 3) continue;

            if(words[i].startsWith("(")){
                words[i] = words[i].substring(1,words[i].length()-1);
                wordsCopy[i] = words[i];
            }
            if(words[i].endsWith(")")){
                words[i] = words[i].substring(0,words[i].length()-2);
                wordsCopy[i] = words[i];
            }
            words[i] = words[i].replaceFirst(REGEX_VOWELS,"");
        }

        for (int i = 0; i < wordsCopy.length; i++) {
            try {
                text = text.replaceAll(wordsCopy[i], words[i]);
            } catch (Exception e) {
                //SKIP
            }            
        }


            
        return text;
    }
    /**
     * Compresses text by getting the most frequent words and turning them into shortcuts
     * @param text
     * @return
     */
    public String compressTextShortcuts(String text){
        String negativeShortcutLookahead = "(?!\\(\\w+\\))";
        HashMap<String,Integer> topFrequencies = getTopFrequencies(getFrequencyMap(text), 20);

        HashMap<String,String> shortcuts = getShortcutsFromMap(topFrequencies);
        for (String word : shortcuts.keySet()) {
            text.replaceFirst(word,"("+shortcuts.get(word)+")");
            text = text.replaceAll(word+negativeShortcutLookahead, shortcuts.get(word));
        }

        return text;
    }

    /**
     * Creates and returns a map of shortcuts based on a frequency map given to it.
     * A frequency map can be created with the method getFrequencyMap().
     * @param topFrequencies - Frequency map of top frequencies
     * @return
     */
    public HashMap<String,String> getShortcutsFromMap(HashMap<String,Integer> topFrequencies){
        HashMap<String,String> shortcuts = new HashMap<>();
        for(String usedWord:topFrequencies.keySet()){
            if(usedWord.length() == 1) continue;
            String shortcut = "";
            for (int i = 0; i < usedWord.length(); i++) {
                shortcut = usedWord.substring(0, i).toUpperCase();
                if(shortcuts.values().contains(shortcut) || shortcut.length() == 0) continue;
                shortcuts.put(usedWord, shortcut);
                break;
            }
        }
        return shortcuts;
    }

    /**
     * Return a map of words as keys, and Integers as their frequencies in given text.
     * @param text - Text to create the frequency map for
     * @return
     */
    public HashMap<String,Integer> getFrequencyMap(String text){
        String[] words = getWords(text);
        HashMap<String,Integer> frequencyMap = new HashMap<>();

        for (String word : words) {
            word = word.toLowerCase();
            if(!frequencyMap.keySet().contains(word)){
                frequencyMap.put(word, 1);
                continue;
            }
            int wordFrequency = frequencyMap.get(word);
            frequencyMap.replace(word, wordFrequency+=1);
        }

        return frequencyMap;
    }

    /**
     * Creates a map of top frequencies from a frequency map.
     * It takes the top x frequencies based on the topX argument.
     * @param frequencyMap
     * @param topX
     * @return
     */
    public HashMap<String,Integer> getTopFrequencies(HashMap<String,Integer> frequencyMap,int topX){
        HashMap<String,Integer> topMap = new HashMap<>();
        
        ArrayList<Integer> topFrequencies = new ArrayList<>();

        for(Integer freq : frequencyMap.values()){
            topFrequencies.add(freq);
        }

        Collections.sort(topFrequencies,Collections.reverseOrder());

        if(topFrequencies.size() > topX){
            topFrequencies = new ArrayList<>(topFrequencies.subList(0, topX));
        }
        

        for(Map.Entry<String,Integer> entry : frequencyMap.entrySet()){
            if (topFrequencies.contains(entry.getValue())) {
                topMap.put(entry.getKey(),entry.getValue());
            }
        }
        return topMap;
    }
    /**
     * Return word count for given text.
     * @param text
     * @return
     */
    public int getWordCount(String text){
        return getWords(text).length;
    }
    /**
     * Return array of words for given text
     * @param text
     * @return
     * @throws PatternSyntaxException
     */
    public String[] getWords(String text) throws PatternSyntaxException{
        return text.split(REGEX_TO_SPLIT_WORDS);
    }

}
