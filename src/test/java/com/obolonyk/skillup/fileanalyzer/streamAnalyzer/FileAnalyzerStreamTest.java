package com.obolonyk.skillup.fileanalyzer.streamAnalyzer;

import com.obolonyk.skillup.fileanalyzer.AbstractFileAnalyzerTest;
import com.obolonyk.skillup.fileanalyzer.FileAnalyzer;

public class FileAnalyzerStreamTest extends AbstractFileAnalyzerTest {
    @Override
    protected FileAnalyzer getFileAnalyzer() {
        return new FileAnalyzerStream();
    }
}
