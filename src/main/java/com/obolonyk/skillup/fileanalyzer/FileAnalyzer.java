package com.obolonyk.skillup.fileanalyzer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FileAnalyzer {

    private String word;
    private List<byte[]> allSentences;


    public FileAnalyzer() {
    }

    public FileAnalyzer(String path, String word) throws IOException {
        this.word = word;
        try (InputStream stream = new FileInputStream(path)) {
            this.allSentences = getAllSentences(stream);
        }
    }

    public void printSentences() throws IOException {
        List<String> sentencesWithWord = this.getSentencesWithWord(allSentences, word);
        for (String sentence : sentencesWithWord) {
            System.out.println(sentence);
        }
    }

    public int getTotalCount() throws IOException {
        List<String> sentencesWithWord = this.getSentencesWithWord(allSentences, word);
        int totalCounter = 0;
        for (String sentence : sentencesWithWord) {
            totalCounter = totalCounter + getCounter(sentence, word);
        }
        return totalCounter;
    }

    List<String> getSentencesWithWord(List<byte[]> sentences, String word) {
        List<String> sentenceWithWord = new ArrayList<>();
        for (byte[] byteSentence : sentences) {
            String sentence = new String(byteSentence);
            if (sentence.contains(word)) {
                sentenceWithWord.add(sentence);
            }
        }
        return sentenceWithWord;
    }

    List<byte[]> getAllSentences(InputStream stream) throws IOException {
        int character = 0;
        List<byte[]> sentences = new ArrayList<>();
        List<Byte> array = new ArrayList<>();
        while (character != -1) {
            character = stream.read();
            array.add((byte) character);
            if (character == '!' || character == '?' || character == '.') {
                byte[] arrayByte = new byte[array.size()];
                for (int i = 0; i < array.size(); i++) {
                    arrayByte[i] = array.get(i);
                }
                sentences.add(arrayByte);
                array = new ArrayList<>();
            }
        }
        return sentences;
    }

    static int getCounter(String sentence, String word) {
        if (word == null) {
            throw new IllegalArgumentException("The word for search was not provided");
        }
        String allWords = sentence.replaceAll("[^\\w]", " ");
        String[] split = allWords.split(" ");
        int counter = 0;
        for (String s : split) {
            if (s.toLowerCase().trim().equals(word.toLowerCase())) {
                counter++;
            }
        }
        return counter;
    }
}
