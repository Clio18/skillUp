package com.obolonyk.skillup.fileanalyzer.streamAnalyzer;

import com.obolonyk.skillup.fileanalyzer.AbstractFileAnalyzerITest;
import com.obolonyk.skillup.fileanalyzer.FileAnalyzer;

public class FileAnalyzerStreamITest extends AbstractFileAnalyzerITest {

    @Override
    protected FileAnalyzer getFileAnalyzer() {
        return new FileAnalyzerStream();
    }
}
