package com.obolonyk.skillup.fileanalyzer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileAnalyzer {
    private static final String TRIPLE_DOT = "...";
    private static final String DOT = ".";
    private static final String QUOTER = "\"";
    private static final String SPACE = " ";
    private static final String EMPTY = "";
    private static final char EMPTY_CHAR = ' ';

    private static final String REG_EX_DOT = "[.]";
    private static final String REG_EX_NOT_A_LETTER = "[^\\w]";
    private static final String REG_EX_SENTENCE_ENDINGS = "[!?.]";

    private String word;
    private String text;

    public FileAnalyzer() {
    }

    public FileAnalyzer(String path, String word) throws IOException {
        this.word = word;
        try (FileInputStream fileInputStream = new FileInputStream(path);
             InputStreamReader stream = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
                     BufferedReader reader=new BufferedReader(stream)){
            this.text = getAllText(reader);
        }
    }

    public FileInfo analyze() {
        List<String> sentencesWithWord = this.getSentencesWithWord(text, word);
        int totalCounter = 0;
        for (String sentence : sentencesWithWord) {
            totalCounter = totalCounter + getCounter(sentence, word);
        }
        return new FileInfo(totalCounter, sentencesWithWord);
    }

    List<String> getSentencesWithWord(String text, String word) {
        String[] sentences = getAllSentences(text);
        List<String> sentenceWithWord = new ArrayList<>();
        for (String sentence : sentences) {
            if (sentence.contains(word.toLowerCase()) && validation(sentence, word.toLowerCase())) {
                sentenceWithWord.add(sentence);
            }
        }
        return sentenceWithWord;
    }

    boolean validation(String sentence, String word) {
        int index = sentence.indexOf(word);
        int length = word.length();
        int target = index + length;
        char[] chars = sentence.toCharArray();
        if (target == chars.length) {
            return true;
        }
        char spaceOrEnd = chars[target];
        return spaceOrEnd == EMPTY_CHAR;
    }

    String getAllText(BufferedReader reader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }

    String[] getAllSentences(String text) {
        String replaceTripleDot = text.toLowerCase().replace(TRIPLE_DOT, DOT);
        String replaceTripleQuoter = replaceTripleDot.replace(QUOTER, EMPTY);
        String replaceAll = replaceTripleQuoter.replaceAll(REG_EX_SENTENCE_ENDINGS, DOT);
        return replaceAll.split(REG_EX_DOT);
    }

    int getSentencesAndReturnItsAmount() {
        List<String> sentencesWithWord = this.getSentencesWithWord(text, word);
        return sentencesWithWord.size();
    }

    static int getCounter(String sentence, String word) {
        if (word == null) {
            throw new IllegalArgumentException("The word for search was not provided");
        }
        String allWords = sentence.replaceAll(REG_EX_NOT_A_LETTER, SPACE);
        String[] split = allWords.split(SPACE);
        int counter = 0;
        for (String s : split) {
            if (s.toLowerCase().trim().equals(word.toLowerCase())) {
                counter++;
            }
        }
        return counter;
    }
}
