package com.obolonyk.skillup.fileanalyzer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static com.obolonyk.skillup.fileanalyzer.defaultAnalyzer.FileAnalyzerDefault.validateWordEnding;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractFileAnalyzerTest {

    protected abstract FileAnalyzer getFileAnalyzer();

    @Test
    @DisplayName("test ValidateWordEnding Is True")
    void testValidateWordEndingIsTrue() {
        String sentence = "Thus the term bean in general usage can refer to a host of different species";
        String word = "bean";
        assertTrue(validateWordEnding(sentence, word));
    }

    @Test
    @DisplayName("test ValidateWordEnding Not Word Return False")
    void testValidateWordEndingNotWordReturnFalse() {
        String sentence = "Thus the term bean in general usage can refer to a host of different species";
        String word = "gen";
        assertFalse(validateWordEnding(sentence, word));
    }

    @Test
    @DisplayName("test Validation Word In The End")
    void testValidationWordInTheEnd() {
        String sentence = "Thus the term bean in general usage can refer to a host of different species";
        String word = "species";
        assertTrue(validateWordEnding(sentence, word));
    }

    @Test
    @DisplayName("test Validation Word In The Beginning")
    void testValidationWordInTheBeginning() {
        String sentence = "Thus the term bean in general usage can refer to a host of different species";
        String word = "Thus";
        assertTrue(validateWordEnding(sentence, word));
    }
}
