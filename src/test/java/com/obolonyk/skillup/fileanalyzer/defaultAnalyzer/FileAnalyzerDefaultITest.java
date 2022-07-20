package com.obolonyk.skillup.fileanalyzer.defaultAnalyzer;

import com.obolonyk.skillup.fileanalyzer.AbstractFileAnalyzerITest;
import com.obolonyk.skillup.fileanalyzer.FileAnalyzer;

class FileAnalyzerDefaultITest extends AbstractFileAnalyzerITest {

    @Override
    protected FileAnalyzer getFileAnalyzer() {
        return new FileAnalyzerDefault();
    }
}
