package com.kuta.compression;

import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class Compressor {
    
    private String text;
    
    
    public Compressor(String text){
        this.text = text;    
    }

    public HashMap<String,Integer> getFrequencyMap(String text){
        String[] words = text.split("[,.:;]*\s");
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

        Collections.sort(topFrequencies);

        if(topFrequencies.size() > topX){
            topFrequencies = (ArrayList<Integer>)topFrequencies.subList(0, topX);
        }
        

        for(Map.Entry<String,Integer> entry : frequencyMap.entrySet()){
            if (topFrequencies.contains(entry.getValue())) {
                topMap.put(entry.getKey(),entry.getValue());
            }
        }
        return topMap;
    }
    

}
