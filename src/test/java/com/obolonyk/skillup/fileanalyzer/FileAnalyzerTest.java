package com.obolonyk.skillup.fileanalyzer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileAnalyzerTest {

    @Test
    @DisplayName("test GetCounter If Word Exist In Sentence")
    void testGetCounterIfWordExistInSentence() {
        String sentence = "Thus the term \"bean\" in general usage can refer to a host of different species.";
        String word = "bean";
        int counter = FileAnalyzer.getCounter(sentence, word);
        assertEquals(1, counter);
    }

    @Test
    @DisplayName("test GetCounter If Word Not Exist In Sentence")
    void testGetCounterIfWordNotExistInSentence() {
        String sentence = "Thus the term \"bean\" in general usage can refer to a host of different species.";
        String word = "java";
        int counter = FileAnalyzer.getCounter(sentence, word);
        assertEquals(0, counter);
    }

    @Test
    @DisplayName("test GetCounter If Word Null Throw Exception")
    void testGetCounterIfWordNullThrowException() {
        String sentence = "Thus the term \"bean\" in general usage can refer to a host of different species.";
        String word = null;
        assertThrows(IllegalArgumentException.class, () -> {
            FileAnalyzer.getCounter(sentence, word);
        });
    }

    @Test
    @DisplayName("test GetCounter If Word Null Throw Exception")
    void testGetCounterIfWordNullThrowExceptionAndCheckMessage() {
        String sentence = "Thus the term \"bean\" in general usage can refer to a host of different species.";
        String word = null;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            FileAnalyzer.getCounter(sentence, word);
        });
        assertEquals("The word for search was not provided", exception.getMessage());
    }

    @Test
    @DisplayName("test GetAllSentences And Check The Size")
    void testGetAllSentencesAndCheckTheSize() throws IOException {
        FileAnalyzer fileAnalyzer = new FileAnalyzer();
        String sentence1 = "Thus the term \"bean\" in general usage can refer to a host of different species!";
        String sentence2 = "Thus the term \"bean\" in general usage can refer to a host of different species?";
        String sentence3 = "Thus the term \"bean\" in general usage can refer to a host of different species.";
        String threeSentences = sentence1 + sentence2 + sentence3;
        InputStream stream = new ByteArrayInputStream(threeSentences.getBytes (Charset.forName("UTF-8")));
        List<byte[]> sentences = fileAnalyzer.getAllSentences(stream);
        assertEquals(3, sentences.size());
    }

    @Test
    @DisplayName("test GetSentencesWithWord And Check Size")
    void testGetSentencesWithWordAndCheckSize(){
        FileAnalyzer fileAnalyzer = new FileAnalyzer();
        String word = "bean";
        String sentence1 = "Thus the term \"bean\" in general usage can refer to a host of different species!";
        String sentence2 = "Thus the term in general usage can refer to a host of different species?";
        String sentence3 = "Thus the term \"bean\" in general usage can refer to a host of different species.";
        List<byte[]> list = Arrays.asList(sentence1.getBytes(StandardCharsets.UTF_8), sentence2.getBytes(StandardCharsets.UTF_8), sentence3.getBytes(StandardCharsets.UTF_8));
        List<String> sentencesWithWord = fileAnalyzer.getSentencesWithWord(list, word);
        assertEquals(2, sentencesWithWord.size());
    }



}