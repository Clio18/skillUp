package com.obolonyk.skillup.fileanalyzer.defaultAnalyzer;

import com.obolonyk.skillup.fileanalyzer.AbstractFileAnalyzerTest;
import com.obolonyk.skillup.fileanalyzer.FileAnalyzer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class FileAnalyzerDefaultTest extends AbstractFileAnalyzerTest {

    FileAnalyzerDefault fileAnalyzer = new FileAnalyzerDefault();

    @Override
    protected FileAnalyzer getFileAnalyzer() {
        return fileAnalyzer;
    }

    @Test
    @DisplayName("test CountWordInSentence If Word Exist In Sentence")
    void testCountWordInSentenceIfWordExistInSentence() {
        String sentence = "Thus the term \"bean\" in general usage can refer to a host of different species.";
        String word = "bean";
        int counter = FileAnalyzerDefault.countWordInSentence(sentence, word);
        assertEquals(1, counter);
    }

    @Test
    @DisplayName("test CountWordInSentence If Word Not Exist In Sentence")
    void testCountWordInSentenceIfWordNotExistInSentence() {
        String sentence = "Thus the term \"bean\" in general usage can refer to a host of different species.";
        String word = "java";
        int counter = FileAnalyzerDefault.countWordInSentence(sentence, word);
        assertEquals(0, counter);
    }

    @Test
    @DisplayName("test CountWordInSentence If Word Null Throw Exception")
    void testCountWordInSentenceIfWordNullThrowException() {
        String sentence = "Thus the term \"bean\" in general usage can refer to a host of different species.";
        String word = null;
        assertThrows(NullPointerException.class, () -> FileAnalyzerDefault.countWordInSentence(sentence, word));
    }

    @Test
    @DisplayName("test CountWordInSentence If Word Null Throw Exception")
    void testCountWordInSentenceIfWordNullThrowExceptionAndCheckMessage() {
        String sentence = "Thus the term \"bean\" in general usage can refer to a host of different species.";
        String word = null;

        NullPointerException exception = assertThrows(NullPointerException.class, () -> FileAnalyzerDefault.countWordInSentence(sentence, word));
        assertEquals("The word for search was null", exception.getMessage());
    }

    @Test
    @DisplayName("test ExtractAllText With Different Endings")
    void testExtractAllTextWithDifferentEndings() throws IOException {
        String sentence1 = "Thus the term \"bean\" in general usage can refer to a host of different species!";
        String sentence2 = "Thus the term \"bean\" in general usage can refer to a host of different species?";
        String sentence3 = "Thus the term \"bean\" in general usage can refer to a host of different species...";
        String sentence4 = "Thus the term \"bean\" in general usage can refer to a host of different species.";
        String threeSentences = sentence1 + sentence2 + sentence3 + sentence4;
        InputStream stream = new ByteArrayInputStream(threeSentences.getBytes(StandardCharsets.UTF_8));
        String text = fileAnalyzer.extractAllText(stream);
        assertEquals(threeSentences.toLowerCase(), text);
    }

    @Test
    @DisplayName("test ExtractAllText Without Endings")
    void testExtractAllTextWithoutEndings() throws IOException {
        String sentence = "Thus the term \"bean\" in general usage can refer to a host of different species";
        InputStream stream = new ByteArrayInputStream(sentence.getBytes(StandardCharsets.UTF_8));
        String text = fileAnalyzer.extractAllText(stream);
        assertEquals(sentence.toLowerCase(), text);
    }

    @Test
    @DisplayName("test ExtractAllSentences")
    void testExtractAllSentences() {
        String sentence1 = "Thus the term \"bean\" in general usage can refer to a host of different species!";
        String sentence2 = "Thus the term \"bean\" in general usage can refer to a host of different species?";
        String sentence3 = "Thus the term \"bean\" in general usage can refer to a host of different species.";
        String threeSentences = sentence1 + sentence2 + sentence3;
        String[] sentences = fileAnalyzer.extractAllSentences(threeSentences);
        assertEquals(3, sentences.length);
    }


    @Test
    @DisplayName("test ExtractSentencesWithWord If Word Exist")
    void testExtractSentencesWithWordIfWordExist() {
        String sentence1 = "Thus the term bean in general usage can refer to a host of different species!";
        String sentence2 = "Thus the term in general usage can refer to a host of different species?";
        String sentence3 = "Thus the term bean in general usage can refer to a host of different species.";
        String[] threeSentences = new String[3];
        threeSentences[0] = sentence1;
        threeSentences[1] = sentence2;
        threeSentences[2] = sentence3;
        String word = "bean";
        List<String> sentencesWithWord = fileAnalyzer.extractSentencesWithWord(threeSentences, word);
        assertEquals(2, sentencesWithWord.size());
    }

    @Test
    @DisplayName("test ExtractSentencesWithWord If Word Not Exist")
    void testExtractSentencesWithWordIfWordNotExist() {
        String sentence1 = "Thus the term \"bean\" in general usage can refer to a host of different species!";
        String sentence2 = "Thus the term in general usage can refer to a host of different species?";
        String sentence3 = "Thus the term \"bean\" in general usage can refer to a host of different species.";
        String[] threeSentences = new String[3];
        threeSentences[0] = sentence1;
        threeSentences[1] = sentence2;
        threeSentences[2] = sentence3;
        String word = "java";
        List<String> sentencesWithWord = fileAnalyzer.extractSentencesWithWord(threeSentences, word);
        assertEquals(0, sentencesWithWord.size());
    }

    @Test
    @DisplayName("test ExtractSentencesWithWord If Word Part Of Other Word")
    void testExtractSentencesWithWordIfWordPartOfOtherWord() {
        String sentence = "Thus the term \"bean\" in general usage can refer to a host of different species.";
        String[] array = new String[1];
        array[0] = sentence;
        String word = "gen";
        List<String> sentencesWithWord = fileAnalyzer.extractSentencesWithWord(array, word);
        assertEquals(0, sentencesWithWord.size());
    }

    @Test
    @DisplayName("test ExtractAllText")
    void testExtractAllText() throws IOException {
        String sentence = "Thus the term ... in general usage can refer to a host of different species.";
        String text = fileAnalyzer.extractAllText(new ByteArrayInputStream(sentence.getBytes()));
        assertEquals(sentence.toLowerCase(), text);
    }
}