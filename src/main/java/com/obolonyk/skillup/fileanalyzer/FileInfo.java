package com.obolonyk.skillup.fileanalyzer;

import java.util.List;

public class FileInfo {
    private int count;
    private List<String> sentences;

    public FileInfo(int count, List<String> sentences) {
        this.count = count;
        this.sentences = sentences;
    }

    public int getCount() {
        return count;
    }

    public List<String> getSentences() {
        return sentences;
    }
}
