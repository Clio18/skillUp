package com.obolonyk.skillup.fileanalyzer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractFileAnalyzerITest {

    String path = "src/test/resources/text.txt";
    String pathWithCyrylica = "src/test/resources/text2.txt";
    FileAnalyzer fileAnalyzer;

    protected abstract FileAnalyzer  getFileAnalyzer();

    @BeforeEach
    void init (){
        fileAnalyzer = getFileAnalyzer();
    }

    @Test
    @DisplayName("Test GetTotalCount With UpperCase Word")
    void testGetTotalCountWithUpperCaseWord() throws IOException {
        String word = "Java";
        assertEquals(12, fileAnalyzer.analyze(word, path).getCount());
    }

    @Test
    @DisplayName("Test GetTotalCount With LowerCase Word")
    void testGetTotalCountWithLowerCaseWord() throws IOException {
        String word = "java";
        assertEquals(12, fileAnalyzer.analyze(word, path).getCount());
    }

    @Test
    @DisplayName("Test GetTotalCount With LowerCase Word And Check Amount Of Sentences")
    void testGetTotalCountWithLowerCaseWordAndCheckAmountOfSentences() throws IOException {
        String word = "java";
        assertEquals(7, fileAnalyzer.analyze(word, path).getSentences().size());
    }

    @Test
    @DisplayName("Test GetTotalCount With LowerCase Not Existing Word And Check Amount Of Sentences")
    void testGetTotalCountWithLowerCaseNotExistingWordAndCheckAmountOfSentences() throws IOException {
        String word = "avaj";
        assertEquals(0, fileAnalyzer.analyze(word, path).getSentences().size());
    }

    @Test
    @DisplayName("Test GetTotalCount With LowerCase Not Existing Word And Check Count")
    void testGetTotalCountWithLowerCaseNotExistingWordAndCheckCount() throws IOException {
        String word = "avaj";
        assertEquals(0, fileAnalyzer.analyze(word, path).getCount());
    }

    @Test
    @DisplayName("Test GetTotalCount With UpperCase Word Path With Cyrylica")
    void testGetTotalCountWithUpperCaseWordPathWithCyrylica() throws IOException {
        String word = "Java";
        assertEquals(8, fileAnalyzer.analyze(word, pathWithCyrylica).getCount());
    }

    @Test
    @DisplayName("Test GetTotalCount With LowerCase Word Path With Cyrylica")
    void testGetTotalCountWithLowerCaseWordPathWithCyrylica() throws IOException {
        String word = "java";
        assertEquals(8, fileAnalyzer.analyze(word, pathWithCyrylica).getCount());
    }

    @Test
    @DisplayName("Test GetTotalCount With LowerCase Word And Check Amount Of Sentences Path With Cyrylica")
    void testGetTotalCountWithLowerCaseWordAndCheckAmountOfSentencesPathWithCyrylica() throws IOException {
        String word = "java";
        assertEquals(5, fileAnalyzer.analyze(word, pathWithCyrylica).getSentences().size());
    }

    @Test
    @DisplayName("Test GetTotalCount With LowerCase Not Existing Word And Check Amount Of Sentences Path With Cyrylica")
    void testGetTotalCountWithLowerCaseNotExistingWordAndCheckAmountOfSentencesPathWithCyrylica() throws IOException {
        String word = "avaj";
        assertEquals(0, fileAnalyzer.analyze(word, pathWithCyrylica).getSentences().size());
    }

    @Test
    @DisplayName("Test GetTotalCount With LowerCase Not Existing Word And Check Count Path With Cyrylica")
    void testGetTotalCountWithLowerCaseNotExistingWordAndCheckCountPathWithCyrylica() throws IOException {
        String word = "avaj";
        assertEquals(0, fileAnalyzer.analyze(word, pathWithCyrylica).getCount());
    }

}
