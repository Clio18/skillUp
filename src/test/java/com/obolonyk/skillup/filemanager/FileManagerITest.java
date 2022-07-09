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
        File emptyDir = new File("empty");
        emptyDir.mkdir();

        File fileDir = new File("new");
        fileDir.mkdir();

        File file = new File("new", "file.txt");
        file.createNewFile();

        File file1 = new File("new", "file1.txt");
        file1.createNewFile();

        File file2 = new File("new", "file2.txt");
        file2.createNewFile();

        File fileDir1 = new File("new", "new1");
        fileDir1.mkdir();

        File file3 = new File("new" + File.separator + "new1" + File.separator + "file3.txt");
        file3.createNewFile();
        File file4 = new File("new" + File.separator + "new1" + File.separator + "file4.txt");
        file4.createNewFile();

        File fileDir2 = new File("new" + File.separator + "new1" + File.separator + "new2");
        fileDir2.mkdir();

        File file5 = new File("new" + File.separator + "new1" + File.separator + "new2" + File.separator + "file5.txt");
        file5.createNewFile();

        File hiddenDir = new File(".dir");
        hiddenDir.mkdir();
    }

    @AfterEach
    void destroy() {
        File file5 = new File("new" + File.separator + "new1" + File.separator + "new2" + File.separator + "file5.txt");
        file5.delete();
        File fileDir2 = new File("new" + File.separator + "new1" + File.separator + "new2");
        fileDir2.delete();

        File file3 = new File("new" + File.separator + "new1" + File.separator + "file3.txt");
        file3.delete();
        File file4 = new File("new" + File.separator + "new1" + File.separator + "file4.txt");
        file4.delete();
        File fileDir1 = new File("new", "new1");
        fileDir1.delete();

        File file = new File("new", "file.txt");
        file.delete();
        File file1 = new File("new", "file1.txt");
        file1.delete();
        File file2 = new File("new", "file2.txt");
        file2.delete();
        File fileDir = new File("new");
        fileDir.delete();

        File emptyDir = new File("empty");
        emptyDir.delete();


        // For copy tests
        File wen5 = new File("wen" + File.separator + "new1" + File.separator + "new2" + File.separator + "file5.txt");
        wen5.delete();
        File wenDir2 = new File("wen" + File.separator + "new1" + File.separator + "new2");
        wenDir2.delete();

        File wen3 = new File("wen" + File.separator + "new1" + File.separator + "file3.txt");
        wen3.delete();
        File wen4 = new File("wen" + File.separator + "new1" + File.separator + "file4.txt");
        wen4.delete();
        File wenDir1 = new File("wen", "new1");
        wenDir1.delete();

        File wen = new File("wen", "file.txt");
        wen.delete();
        File wen1 = new File("wen", "file1.txt");
        wen1.delete();
        File wen2 = new File("wen", "file2.txt");
        wen2.delete();
        File wenDir = new File("wen");
        wenDir.delete();

        File hiddenDir = new File(".dir");
        hiddenDir.delete();
    }

    @Test
    @DisplayName("Test CountDirs")
    void testCountDirs() {
        int dirs = FileManager.countDirs("new");
        assertEquals(2, dirs);
    }

    @Test
    @DisplayName("Test CountFiles")
    void testCountFiles() {
        int files = FileManager.countFiles("new");
        assertEquals(6, files);
    }

    @Test
    @DisplayName("Test CountDirs On Empty Dir")
    void testCountDirsOnEmptyDir() {
        int dirs = FileManager.countDirs("empty");
        assertEquals(0, dirs);
    }

    @Test
    @DisplayName("Test CountFiles On Empty Dir")
    void testCountFilesOnEmptyDir() {
        int files = FileManager.countFiles("empty");
        assertEquals(0, files);
    }

    @Test
    @DisplayName("Test Move And Check If True")
    void testMoveAndCheckIfTrue() {
        assertTrue(FileManager.move("empty", "new" + File.separator + "newEmpty"));
    }

    @Test
    @DisplayName("Test Move And Check If Dir Is Gone")
    void testMoveAndCheckIfDirIsGone() {
        File empty = new File("empty");
        assertNotNull(empty.listFiles());
        FileManager.move("empty", "new" + File.separator + "newEmpty");
        assertNull(empty.listFiles());
        File newEmpty = new File("new", "newEmpty");
        newEmpty.delete();
    }

    @Test
    @DisplayName("Test Move And Check If Target Dir Became Plus One File")
    void testMoveAndCheckIfTargetDirBecamePlusOneFile() {
        File moveTo = new File("move");
        moveTo.mkdir();
        File targetForMove = new File("new", "target");
        moveTo.mkdir();

        int dirs = FileManager.countDirs("new");
        FileManager.move("move", "new" + File.separator + "target");
        int dirsPlusOne = FileManager.countDirs("new");
        assertEquals(dirs + 1, dirsPlusOne);

        targetForMove.delete();
    }

    @Test
    @DisplayName("Test Copy And Check Files Count")
    void testCopyAndCheckFilesCount() throws IOException {
        File newDir = new File("wen");
        newDir.mkdir();
        FileManager.copy("new", "wen");
        assertEquals(FileManager.countFiles("new"), FileManager.countFiles("wen"));
    }

    @Test
    @DisplayName("Test Copy And Check Dirs Count")
    void testCopyAndCheckDirsCount() throws IOException {
        File newDir = new File("wen");
        newDir.mkdir();
        FileManager.copy("new", "wen");
        assertEquals(FileManager.countDirs("new"), FileManager.countDirs("wen"));
    }

    @Test
    @DisplayName("Test Copy And Check Files Text")
    void testCopyAndCheckFilesText() throws IOException {
        File newDir = new File("wen");
        newDir.mkdir();
        OutputStream outputStream = new FileOutputStream("new" + File.separator + "file.txt");
        String word = "Java";
        outputStream.write(word.getBytes());
        FileManager.copy("new", "wen");
        InputStream inputStream = new FileInputStream("wen" + File.separator + "file.txt");
        String copyWord = new String(inputStream.readAllBytes());
        assertEquals(word, copyWord);
    }

    @Test
    @DisplayName("Test Copy In The Wrong Path And Check Exception Message")
    void testCopyInTheWrongPathAndCheckExceptionMessage() throws IOException {
        File newDirParent = new File("www");
        newDirParent.mkdir();
        File newDir = new File("www", "www");
        File sameDir = new File("new", "www");
        newDir.mkdir();
        sameDir.mkdir();

        NullPointerException nullPointerException = assertThrows(NullPointerException.class, () -> FileManager.copy("www", "new"));
        assertEquals("The folder was not created on path: " + "new" + File.separator + "www", nullPointerException.getMessage());
        sameDir.delete();
        newDir.delete();
        newDirParent.delete();
    }

    @Test
    @DisplayName("Test CountFiles On Hidden Path And Check Exception Message")
    void testCountFilesOnHiddenPathAndCheckExceptionMessage() {
        File hiddenDir = new File(".dir");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            FileManager.countFiles(hiddenDir.getPath());
        });
        assertEquals("Provided path leads to hidden file! Access denied", exception.getMessage());
    }
}
