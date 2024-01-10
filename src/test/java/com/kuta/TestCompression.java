package com.kuta;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.jupiter.api.Test;

import com.kuta.compression.Compressor;

public class TestCompression {
    Compressor compressor = new Compressor();

    @Test
    public void testTextCompression(){
        String textToCompress = "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc.";
        String compressedText = compressor.getCompressedText(textToCompress);

        assertTrue(compressedText.length() < textToCompress.length());
    }

    @Test
    public void testWordCount(){
        String countMyWords = "One,two three.Four. 5";
        int wordCount = 5;
        assertEquals(wordCount, compressor.getWordCount(countMyWords));
    }
    
}
