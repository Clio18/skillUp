package com.obolonyk.skillup.fileanalyzer;

import java.io.IOException;

public class Executor {
    public static void main(String[] args) throws IOException {
        String word = "Java";
        String path = "text.txt";

        FileAnalyzer fileAnalyzer = new FileAnalyzer(path, word);
        FileInfo fileInfo = fileAnalyzer.analyze();
        System.out.println(fileInfo.getCount());
        for (String sentence : fileInfo.getSentences()) {
            System.out.println(sentence);
        }
    }
}
