package com.obolonyk.skillup.fileanalyzer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileAnalyzerITest {

    String path = "src/main/resources/text.txt";

    @Test
    @DisplayName("Test GetTotalCount With UpperCase Word")
    void testGetTotalCountWithUpperCaseWord() throws IOException {
        String word = "Java";
        FileAnalyzer fileAnalyzer = new FileAnalyzer(path, word);
        assertEquals(11, fileAnalyzer.analyze().getCount());
    }

    @Test
    @DisplayName("Test GetTotalCount With LowerCase Word")
    void testGetTotalCountWithLowerCaseWord() throws IOException {
        String word = "java";
        FileAnalyzer fileAnalyzer = new FileAnalyzer(path, word);
        assertEquals(11, fileAnalyzer.analyze().getCount());
    }

    @Test
    @DisplayName("Test PrintSentencesAndReturnItsAmount")
    void testPrintSentencesAndReturnItsAmount() throws IOException {
        String wordJava = "java";
        FileAnalyzer fileAnalyzerForJava = new FileAnalyzer(path, wordJava);
        assertEquals(6, fileAnalyzerForJava.printSentencesAndReturnItsAmount());
        String wordBean = "bean";
        FileAnalyzer fileAnalyzerForBean = new FileAnalyzer(path, wordBean);
        assertEquals(4, fileAnalyzerForBean.printSentencesAndReturnItsAmount());
    }
}
