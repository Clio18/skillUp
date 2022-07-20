package com.obolonyk.skillup.fileanalyzer;

import java.io.IOException;

public interface FileAnalyzer {

    FileInfo analyze(String word, String path) throws IOException;
}
