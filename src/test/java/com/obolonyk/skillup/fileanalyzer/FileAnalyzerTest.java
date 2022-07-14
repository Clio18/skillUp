package com.obolonyk.skillup.fileanalyzer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
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
        assertThrows(IllegalArgumentException.class, () -> FileAnalyzer.getCounter(sentence, word));
    }

    @Test
    @DisplayName("test GetCounter If Word Null Throw Exception")
    void testGetCounterIfWordNullThrowExceptionAndCheckMessage() {
        String sentence = "Thus the term \"bean\" in general usage can refer to a host of different species.";
        String word = null;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> FileAnalyzer.getCounter(sentence, word));
        assertEquals("The word for search was not provided", exception.getMessage());
    }


    @Test
    @DisplayName("test GetAllText With Different Endings")
    void testGetAllTextWithDifferentEndings() throws IOException {
        FileAnalyzer fileAnalyzer = new FileAnalyzer();
        String sentence1 = "Thus the term \"bean\" in general usage can refer to a host of different species!";
        String sentence2 = "Thus the term \"bean\" in general usage can refer to a host of different species?";
        String sentence3 = "Thus the term \"bean\" in general usage can refer to a host of different species...";
        String sentence4 = "Thus the term \"bean\" in general usage can refer to a host of different species.";
        String threeSentences = sentence1 + sentence2 + sentence3 + sentence4;
        InputStream stream = new ByteArrayInputStream(threeSentences.getBytes(StandardCharsets.UTF_8));
        String text = fileAnalyzer.getAllText(stream);
        assertEquals(threeSentences, text);
    }

    @Test
    @DisplayName("test GetAllText Without Endings")
    void testGetAllTextWithoutEndings() throws IOException {
        FileAnalyzer fileAnalyzer = new FileAnalyzer();
        String sentence = "Thus the term \"bean\" in general usage can refer to a host of different species";
        InputStream stream = new ByteArrayInputStream(sentence.getBytes(StandardCharsets.UTF_8));
        String text = fileAnalyzer.getAllText(stream);
        assertEquals(sentence, text);
    }

    @Test
    @DisplayName("test GetAllSentences")
    void testGetAllSentences() {
        FileAnalyzer fileAnalyzer = new FileAnalyzer();
        String sentence1 = "Thus the term \"bean\" in general usage can refer to a host of different species!";
        String sentence2 = "Thus the term \"bean\" in general usage can refer to a host of different species?";
        String sentence3 = "Thus the term \"bean\" in general usage can refer to a host of different species.";
        String threeSentences = sentence1 + sentence2 + sentence3;
        String[] sentences = fileAnalyzer.getAllSentences(threeSentences);
        assertEquals(3, sentences.length);
    }


    @Test
    @DisplayName("test GetSentencesWithWord If Word Exist")
    void testGetSentencesWithWordIfWordExist() {
        FileAnalyzer fileAnalyzer = new FileAnalyzer();
        String sentence1 = "Thus the term bean in general usage can refer to a host of different species!";
        String sentence2 = "Thus the term in general usage can refer to a host of different species?";
        String sentence3 = "Thus the term bean in general usage can refer to a host of different species.";
        String [] threeSentences = new String[3];
        threeSentences[0] = sentence1;
        threeSentences[1] = sentence2;
        threeSentences[2] = sentence3;
        String word = "bean";
        List<String> sentencesWithWord = fileAnalyzer.getSentencesWithWord(threeSentences, word);
        assertEquals(2, sentencesWithWord.size());
    }

    @Test
    @DisplayName("test GetSentencesWithWord If Word Not Exist")
    void testGetSentencesWithWordIfWordNotExist() {
        FileAnalyzer fileAnalyzer = new FileAnalyzer();
        String sentence1 = "Thus the term \"bean\" in general usage can refer to a host of different species!";
        String sentence2 = "Thus the term in general usage can refer to a host of different species?";
        String sentence3 = "Thus the term \"bean\" in general usage can refer to a host of different species.";
        String [] threeSentences = new String[3];
        threeSentences[0] = sentence1;
        threeSentences[1] = sentence2;
        threeSentences[2] = sentence3;
        String word = "java";
        List<String> sentencesWithWord = fileAnalyzer.getSentencesWithWord(threeSentences, word);
        assertEquals(0, sentencesWithWord.size());
    }

    @Test
    @DisplayName("test GetSentencesWithWord If Word Part Of Other Word")
    void testGetSentencesWithWordIfWordPartOfOtherWord() {
        FileAnalyzer fileAnalyzer = new FileAnalyzer();
        String sentence = "Thus the term \"bean\" in general usage can refer to a host of different species.";
        String [] array = new String[1];
        array[0] = sentence;
        String word = "gen";
        List<String> sentencesWithWord = fileAnalyzer.getSentencesWithWord(array, word);
        assertEquals(0, sentencesWithWord.size());
    }

    @Test
    @DisplayName("test Validation Word In Sentence Is True")
    void testValidationWordInSentenceIsTrue() {
        FileAnalyzer fileAnalyzer = new FileAnalyzer();
        String sentence = "Thus the term bean in general usage can refer to a host of different species";
        String word = "bean";
        assertTrue(fileAnalyzer.validateThatWordEndsWithEmptySpace(sentence, word));
    }

    @Test
    @DisplayName("test Validation Not Word Return False")
    void testValidationNotWordReturnFalse() {
        FileAnalyzer fileAnalyzer = new FileAnalyzer();
        String sentence = "Thus the term bean in general usage can refer to a host of different species";
        String word = "gen";
        assertFalse(fileAnalyzer.validateThatWordEndsWithEmptySpace(sentence, word));
    }

    @Test
    @DisplayName("test Validation Word In The End")
    void testValidationWordInTheEnd() {
        FileAnalyzer fileAnalyzer = new FileAnalyzer();
        String sentence = "Thus the term bean in general usage can refer to a host of different species";
        String word = "species";
        assertTrue(fileAnalyzer.validateThatWordEndsWithEmptySpace(sentence, word));
    }

    @Test
    @DisplayName("test Validation Word In The Beginning")
    void testValidationWordInTheBeginning() {
        FileAnalyzer fileAnalyzer = new FileAnalyzer();
        String sentence = "Thus the term bean in general usage can refer to a host of different species";
        String word = "Thus";
        assertTrue(fileAnalyzer.validateThatWordEndsWithEmptySpace(sentence, word));
    }

    @Test
    @DisplayName("test GetAllText")
    void testGetAllText () throws IOException {
        FileAnalyzer fileAnalyzer = new FileAnalyzer();
        String sentence = "Thus the term ... in general usage can refer to a host of different species.";
        String text = fileAnalyzer.getAllText(new ByteArrayInputStream(sentence.getBytes()));
        assertEquals(sentence, text);
    }


}