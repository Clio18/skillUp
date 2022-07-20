package com.obolonyk.skillup.fileanalyzer.streamAnalyzer;

import com.obolonyk.skillup.fileanalyzer.FileAnalyzer;
import com.obolonyk.skillup.fileanalyzer.FileInfo;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FileAnalyzerStream implements FileAnalyzer {

    private static final Pattern REG_EX_SENTENCE_SPLIT_PATTERN = Pattern.compile("((?<!\\.)\\.{3}(?!\\.))|[.!?]");
    private static final Pattern REG_EX_NOT_A_LETTER_PATTERN = Pattern.compile("[^\\w]");

    @Override
    public FileInfo analyze(String search, String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {

            List<String> sentencesWithWord = reader
                    .lines()
                    .flatMap(line -> Arrays.stream(REG_EX_SENTENCE_SPLIT_PATTERN.split(line)))
                    .filter(sentences -> sentences.toLowerCase().contains(search.toLowerCase()) && validateWordEnding(sentences, search))
                    .collect(Collectors.toList());

            int count = (int) sentencesWithWord.stream().flatMap(word -> Arrays.stream(REG_EX_NOT_A_LETTER_PATTERN.split(word)))
                    .filter(word -> word.equalsIgnoreCase(search))
                    .count();

            return new FileInfo(count, sentencesWithWord);
        }
    }


    static boolean validateWordEnding(String sentence, String word) {
        int index = sentence.toLowerCase().indexOf(word.toLowerCase());
        int length = word.length();
        int target = index + length;
        char[] chars = sentence.toLowerCase().toCharArray();
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

}
