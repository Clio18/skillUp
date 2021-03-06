package com.obolonyk.skillup.filemanager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileManagerTest {

    @Test
    @DisplayName("Test GetListOfFiles If Path Is Null And Check Exception")
    void testGetListOfFilesIfPathIsNullAndCheckException (){
        assertThrows(IllegalArgumentException.class,  () -> FileManager.getListOfFiles(null));
    }

    @Test
    @DisplayName("Test GetListOfFiles If Path Is Null And Check Exception Message")
    void testGetListOfFilesIfPathIsNullAndCheckExceptionMessage (){
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> FileManager.getListOfFiles(null));
        assertEquals("Please provide a path", illegalArgumentException.getMessage());
    }

//    @Test
//    @DisplayName("Test GetListOfFiles If Path Incorrect And Check Exception")
//    void testGetListOfFilesIfPathIsIncorrectAndCheckException (){
//        assertThrows(NullPointerException.class,  () -> FileManager.getListOfFiles("wrong" + File.separator + "path"));
//    }

//    @Test
//    @DisplayName("Test GetListOfFiles If Path Incorrect And Check Exception Message")
//    void testGetListOfFilesIfPathIsIncorrectAndCheckExceptionMessage (){
//        NullPointerException nullPointerException = assertThrows(NullPointerException.class, () -> FileManager.getListOfFiles("wrong/path"));
//        assertEquals("The files do not exist on provided path " + "wrong/path", nullPointerException.getMessage());
//    }

    @Test
    @DisplayName("Test Move With The Same From And To Path And Return False")
    void testMoveWithTheSameFromAndToPathAndReturnFalse(){
       assertFalse(FileManager.move("some" + File.separator + "path", "some" + File.separator + "path"));
    }

}
