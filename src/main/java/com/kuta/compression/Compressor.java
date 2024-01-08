package com.kuta.compression;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.regex.PatternSyntaxException;

public class Compressor {
    
    public final String REGEX_TO_SPLIT_WORDS = "([,.:;]*\s+|[,.:;]+\s*)";
    public final String REGEX_VOWELS = "[aeiouyAEIOUYáéíóůúýÁÉÍÓÚŮÝ]";
    
    public Compressor(){

    }

    

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

    public String compressTextShortcuts(String text){
        HashMap<String,Integer> topFrequencies = getTopFrequencies(getFrequencyMap(text), 20);

        HashMap<String,String> shortcuts = getShortcutsFromMap(topFrequencies);
        for (String word : shortcuts.keySet()) {
            text.replaceFirst(word,"("+shortcuts.get(word)+")");
            text = text.replaceAll(word, shortcuts.get(word));
        }

        return text;
    }

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

    public int getWordCount(String text){
        return getWords(text).length;
    }
    
    public String[] getWords(String text) throws PatternSyntaxException{
        return text.split(REGEX_TO_SPLIT_WORDS);
    }

}
