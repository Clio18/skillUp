package com.obolonyk.skillup.fileanalyzer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileAnalyzerITest {

    String path = "text.txt";

    @Test
    @DisplayName("Test GetTotalCount With UpperCase Word")
    void testGetTotalCountWithUpperCaseWord() throws IOException {
        String word = "Java";
        FileAnalyzer fileAnalyzer = new FileAnalyzer();
        assertEquals(11, fileAnalyzer.analyze(word, path).getCount());
    }

    @Test
    @DisplayName("Test GetTotalCount With LowerCase Word")
    void testGetTotalCountWithLowerCaseWord() throws IOException {
        String word = "java";
        FileAnalyzer fileAnalyzer = new FileAnalyzer();
        assertEquals(11, fileAnalyzer.analyze(word, path).getCount());
    }

    @Test
    @DisplayName("Test GetTotalCount With LowerCase Word And Check Amount Of Sentences")
    void testGetTotalCountWithLowerCaseWordAndCheckAmountOfSentences() throws IOException {
        String word = "java";
        FileAnalyzer fileAnalyzer = new FileAnalyzer();
        assertEquals(6, fileAnalyzer.analyze(word, path).getSentences().size());
    }

    @Test
    @DisplayName("Test GetTotalCount With LowerCase Not Existing Word And Check Amount Of Sentences")
    void testGetTotalCountWithLowerCaseNotExistingWordAndCheckAmountOfSentences() throws IOException {
        String word = "avaj";
        FileAnalyzer fileAnalyzer = new FileAnalyzer();
        assertEquals(0, fileAnalyzer.analyze(word, path).getSentences().size());
    }

    @Test
    @DisplayName("Test GetTotalCount With LowerCase Not Existing Word And Check Count")
    void testGetTotalCountWithLowerCaseNotExistingWordAndCheckCount() throws IOException {
        String word = "avaj";
        FileAnalyzer fileAnalyzer = new FileAnalyzer();
        assertEquals(0, fileAnalyzer.analyze(word, path).getCount());
    }
}
