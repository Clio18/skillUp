package com.obolonyk.skillup.fileanalyzer;

import java.io.IOException;

public class Executor {
    public static void main(String[] args) throws IOException {
        String word = "Java";
        String path = "src/main/resources/text.txt";

        FileAnalyzer fileAnalyzer = new FileAnalyzer(path, word);
        fileAnalyzer.printSentences();
        int totalCount = fileAnalyzer.getTotalCount();
        System.out.println(totalCount);
    }
}
