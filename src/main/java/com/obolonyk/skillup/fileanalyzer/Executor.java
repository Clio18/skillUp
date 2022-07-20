package com.obolonyk.skillup.fileanalyzer;
import com.obolonyk.skillup.fileanalyzer.defaultAnalyzer.FileAnalyzerDefault;
import com.obolonyk.skillup.fileanalyzer.streamAnalyzer.FileAnalyzerStream;

import java.io.IOException;

public class Executor {
    public static void main(String[] args) throws IOException {
        String word = "Java";
        String path = "src/test/resources/text.txt";

        FileAnalyzer fileAnalyzer = new FileAnalyzerDefault();
        FileInfo fileInfo = fileAnalyzer.analyze(word, path);
        System.out.println(fileInfo.getCount());
        System.out.println(fileInfo.getSentences().size());

        System.out.println("===============================");

        FileAnalyzer fileAnalyzerStream = new FileAnalyzerStream();
        FileInfo fileInfoStream = fileAnalyzerStream.analyze(word, path);
        System.out.println(fileInfoStream.getCount());
        System.out.println(fileInfoStream.getSentences().size());
    }
}
