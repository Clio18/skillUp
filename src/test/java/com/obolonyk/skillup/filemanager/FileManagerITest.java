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
    @DisplayName("Test CountFiles")
    void testCountFiles() {
        int files = FileManager.countFiles("src/main/resources/new");
        assertEquals(6, files);
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

    @Test
    @DisplayName("Test Move And Check If True")
    void testMoveAndCheckIfTrue() {
        assertTrue(FileManager.move("src/main/resources/empty", "src/main/resources/new/newempty"));
    }

    @Test
    @DisplayName("Test Move And Check If Dir Is Gone")
    void testMoveAndCheckIfDirIsGone() {
        File empty = new File("src/main/resources/empty");
        assertNotNull(empty.listFiles());
        FileManager.move("src/main/resources/empty", "src/main/resources/new/newempty");
        assertNull(empty.listFiles());
        File newempty = new File("src/main/resources/new/newempty");
        newempty.delete();
    }

    @Test
    @DisplayName("Test Move And Check If Target Dir Became Plus One File")
    void testMoveAndCheckIfTargetDirBecamePlusOneFile() {
        File moveTo = new File("src/main/resources/move");
        moveTo.mkdir();
        File targetForMove = new File("src/main/resources/new/target");
        moveTo.mkdir();

        int dirs = FileManager.countDirs("src/main/resources/new");
        FileManager.move("src/main/resources/move", "src/main/resources/new/target");
        int dirsPlusOne = FileManager.countDirs("src/main/resources/new");
        assertEquals(dirs + 1, dirsPlusOne);

        targetForMove.delete();
    }
}
