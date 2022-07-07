package com.obolonyk.skillup.fileanalyzer;
import java.io.IOException;

public class Executor {
    public static void main(String[] args) throws IOException {
        String word = "Java";
        String path = "src/test/resources/text.txt";

        FileAnalyzer fileAnalyzer = new FileAnalyzer();
        FileInfo fileInfo = fileAnalyzer.analyze(word, path);
        System.out.println(fileInfo.getCount());
        for (String sentence : fileInfo.getSentences()) {
            System.out.println(sentence);
        }
    }
}
