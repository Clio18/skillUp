package com.obolonyk.skillup.filemanager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;

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


        // For copy tests
        File wen5 = new File("src/main/resources/wen/new1/new2/file5.txt");
        wen5.delete();
        File wenDir2 = new File("src/main/resources/wen/new1/new2");
        wenDir2.delete();

        File wen3 = new File("src/main/resources/wen/new1/file3.txt");
        wen3.delete();
        File wen4 = new File("src/main/resources/wen/new1/file4.txt");
        wen4.delete();
        File wenDir1 = new File("src/main/resources/wen/new1");
        wenDir1.delete();

        File wen = new File("src/main/resources/wen/file.txt");
        wen.delete();
        File wen1 = new File("src/main/resources/wen/file1.txt");
        wen1.delete();
        File wen2 = new File("src/main/resources/wen/file2.txt");
        wen2.delete();
        File wenDir = new File("src/main/resources/wen");
        wenDir.delete();
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
        assertTrue(FileManager.move("src/main/resources/empty", "src/main/resources/new/newEmpty"));
    }

    @Test
    @DisplayName("Test Move And Check If Dir Is Gone")
    void testMoveAndCheckIfDirIsGone() {
        File empty = new File("src/main/resources/empty");
        assertNotNull(empty.listFiles());
        FileManager.move("src/main/resources/empty", "src/main/resources/new/newEmpty");
        assertNull(empty.listFiles());
        File newEmpty = new File("src/main/resources/new/newEmpty");
        newEmpty.delete();
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

    @Test
    @DisplayName("Test Copy And Check Files Count")
    void testCopyAndCheckFilesCount() throws IOException {
        File newDir = new File("src/main/resources/wen");
        newDir.mkdir();
        FileManager.copy("src/main/resources/new", "src/main/resources/wen");
        assertEquals(FileManager.countFiles("src/main/resources/new"), FileManager.countFiles("src/main/resources/wen"));
    }

    @Test
    @DisplayName("Test Copy And Check Dirs Count")
    void testCopyAndCheckDirsCount() throws IOException {
        File newDir = new File("src/main/resources/wen");
        newDir.mkdir();
        FileManager.copy("src/main/resources/new", "src/main/resources/wen");
        assertEquals(FileManager.countDirs("src/main/resources/new"), FileManager.countDirs("src/main/resources/wen"));
    }

    @Test
    @DisplayName("Test Copy And Check Files Text")
    void testCopyAndCheckFilesText() throws IOException {
        File newDir = new File("src/main/resources/wen");
        newDir.mkdir();
        OutputStream outputStream = new FileOutputStream("src/main/resources/new/file.txt");
        String word = "Java";
        outputStream.write(word.getBytes());
        FileManager.copy("src/main/resources/new", "src/main/resources/wen");
        InputStream inputStream = new FileInputStream("src/main/resources/wen/file.txt");
        String copyWord = new String(inputStream.readAllBytes());
        assertEquals(word, copyWord);
    }
}
