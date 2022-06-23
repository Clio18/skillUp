package com.obolonyk.skillup.filemanager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

class FileManagerITest {

    @BeforeEach
    void init() throws IOException {
        File emptyDir = new File("src/main/resources/empty");
        emptyDir.mkdir();

        File fileDir = new File("src/main/resources/new");
        fileDir.mkdir();

        File file = new File("src/main/resources/new/file.txt");
        file.createNewFile();
        File file1 = new File("src/main/resources/new/file1.txt");
        file1.createNewFile();
        File file2 = new File("src/main/resources/new/file2.txt");
        file2.createNewFile();

        File fileDir1 = new File("src/main/resources/new/new1");
        fileDir1.mkdir();

        File file3 = new File("src/main/resources/new/new1/file3.txt");
        file3.createNewFile();
        File file4 = new File("src/main/resources/new/new1/file4.txt");
        file4.createNewFile();

        File fileDir2 = new File("src/main/resources/new/new1/new2");
        fileDir2.mkdir();

        File file5 = new File("src/main/resources/new/new1/new2/file5.txt");
        file5.createNewFile();
    }

    @AfterEach
    void destroy() {
        File file5 = new File("src/main/resources/new/new1/new2/file5.txt");
        file5.delete();
        File fileDir2 = new File("src/main/resources/new/new1/new2");
        fileDir2.delete();

        File file3 = new File("src/main/resources/new/new1/file3.txt");
        file3.delete();
        File file4 = new File("src/main/resources/new/new1/file4.txt");
        file4.delete();
        File fileDir1 = new File("src/main/resources/new/new1");
        fileDir1.delete();

        File file = new File("src/main/resources/new/file.txt");
        file.delete();
        File file1 = new File("src/main/resources/new/file1.txt");
        file1.delete();
        File file2 = new File("src/main/resources/new/file2.txt");
        file2.delete();
        File fileDir = new File("src/main/resources/new");
        fileDir.delete();

        File emptyDir = new File("src/main/resources/empty");
        emptyDir.delete();
    }

    @Test
    @DisplayName("Test CountDirs")
    void testCountDirs() {
        int dirs = FileManager.countDirs("src/main/resources/new");
        assertEquals(2, dirs);
    }

    @Test
    @DisplayName("Test CountDirs On Empty Dir")
    void testCountDirsOnEmptyDir() {
        int dirs = FileManager.countDirs("src/main/resources/empty");
        assertEquals(0, dirs);
    }

    @Test
    @DisplayName("Test CountFiles On Empty Dir")
    void testCountFilesOnEmptyDir() {
        int files = FileManager.countFiles("src/main/resources/empty");
        assertEquals(0, files);
    }
}
