package com.obolonyk.skillup.fileanalyzer.defaultAnalyzer;

import com.obolonyk.skillup.fileanalyzer.FileAnalyzer;
import com.obolonyk.skillup.fileanalyzer.FileInfo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FileAnalyzerDefault implements FileAnalyzer {

    private static final Pattern REG_EX_NOT_A_LETTER_PATTERN = Pattern.compile("[^\\w]");
    private static final Pattern REG_EX_SENTENCE_SPLIT_PATTERN = Pattern.compile("((?<!\\.)\\.{3}(?!\\.))|[.!?]");

    @Override
    public FileInfo analyze(String word, String path) throws IOException {
        String text = extractAllText(path);
        String[] sentences = extractAllSentences(text);
        List<String> sentencesWithWord = extractSentencesWithWord(sentences, word);
        int totalCounter = 0;
        for (String sentence : sentencesWithWord) {
            totalCounter += countWordInSentence(sentence, word);
        }
        return new FileInfo(totalCounter, sentencesWithWord);
    }

    List<String> extractSentencesWithWord(String[] sentences, String word) {
        List<String> sentenceWithWord = new ArrayList<>();
        for (String sentence : sentences) {
            if (sentence.contains(word.toLowerCase()) && validateWordEnding(sentence, word.toLowerCase())) {
                sentenceWithWord.add(sentence);
            }
        }
        return sentenceWithWord;
    }

    public static boolean validateWordEnding(String sentence, String word) {
        int index = sentence.indexOf(word);
        int length = word.length();
        int target = index + length;
        char[] chars = sentence.toCharArray();
        if (target == chars.length) {
            return true;
        }
        char spaceOrEnd = chars[target];
        return (spaceOrEnd == ' ' ||
                spaceOrEnd == ';' ||
                spaceOrEnd == ':' ||
                spaceOrEnd == '\"' ||
                spaceOrEnd == ',');
    }

    String[] extractAllSentences(String text) {
        return REG_EX_SENTENCE_SPLIT_PATTERN.split(text);
    }

    static int countWordInSentence(String sentence, String word) {
        if (word == null) {
            throw new NullPointerException("The word for search was null");
        }
        String[] split = REG_EX_NOT_A_LETTER_PATTERN.split(sentence);
        int counter = 0;
        for (String s : split) {
            if (s.toLowerCase().trim().equals(word.toLowerCase())) {
                counter++;
            }
        }
        return counter;
    }

    String extractAllText(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString().toLowerCase();
    }

    private String extractAllText(String path) throws IOException {
        try (InputStream inputStream = new FileInputStream(path)) {
            return extractAllText(inputStream);
        }
    }
}
