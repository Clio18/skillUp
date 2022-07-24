package com.obolonyk.skillup.serialization;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static com.obolonyk.skillup.serialization.SerializationMessageCustomDao.*;

class SerializationMessageCustomDaoTest {

    @Test
    @DisplayName("test Load After Save Message")
    void testLoadAfterSaveMessage () throws IOException {
        Message message = new Message(new Date(), "Message", 11.1);
        SerializationMessageCustomDao serializationMessageCustomDao = new SerializationMessageCustomDao();
        serializationMessageCustomDao.save(message);
        Message loadedMessage = serializationMessageCustomDao.load();
        assertEquals(message, loadedMessage);
    }

    @Test
    @DisplayName("test Convert Long To Byte Array And Check Length")
    void testConvertLongToByteArrayAndCheckLength(){
        long number = 0;
        byte[] bytes = longToByteArray(number);
        assertEquals(8, bytes.length);
    }

    @Test
    @DisplayName("test Convert Long To Byte Array And Check Result")
    void testConvertLongToByteArrayAndCheckResult(){
        long number = 0;
        byte[] bytes = longToByteArray(number);
        byte [] expected = new byte[]{0, 0, 0, 0, 0, 0, 0, 0};
        assertArrayEquals(expected, bytes);
    }

    @Test
    @DisplayName("test Convert Zero Double To Byte Array And Check Length")
    void testConvertZeroDoubleToByteArrayAndCheckLength(){
        double number = 0.0;
        byte[] bytes = doubleToByteArray(number);
        assertEquals(8, bytes.length);
    }

    @Test
    @DisplayName("test Convert Double To Byte Array And Check Length")
    void testConvertDoubleToByteArrayAndCheckLength(){
        double number = 0.1;
        byte[] bytes = doubleToByteArray(number);
        assertEquals(8, bytes.length);
    }

    @Test
    @DisplayName("test Convert Zero Double To Byte Array And Check Result")
    void testConvertZeroDoubleToByteArrayAndCheckResult(){
        double number = 0.0;
        byte[] bytes = doubleToByteArray(number);
        byte [] expected = new byte[]{0, 0, 0, 0, 0, 0, 0, 0};
        assertArrayEquals(expected, bytes);
    }

    @Test
    @DisplayName("test Convert Int To Byte Array And Check Length")
    void testConvertIntToByteArrayAndCheckLength(){
        int number = 0;
        byte[] bytes = intToByteArray(number);
        assertEquals(4, bytes.length);
    }

    @Test
    @DisplayName("test Convert Int To Byte Array And Check Result")
    void testConvertIntToByteArrayAndCheckResult(){
        int number = 0;
        byte[] bytes = intToByteArray(number);
        byte [] expected = new byte[]{0, 0, 0, 0};
        assertArrayEquals(expected, bytes);
    }

    @Test
    @DisplayName("test Convert Byte Array To Long And Check Length")
    void testConvertByteArrayToLongAndCheckLength(){
        byte[] bytes = new byte[8];
        long number = byteArrayToLong(bytes);
        assertEquals(0, number);
    }

    @Test
    @DisplayName("test Convert Byte Array With Small Size To Long And Throw Exception")
    void testConvertByteArrayWithSmallSizeToLongAndThrowException(){
        byte[] bytes = new byte[7];
        ArrayIndexOutOfBoundsException exception = assertThrows(ArrayIndexOutOfBoundsException.class, () -> byteArrayToLong(bytes));
        assertEquals("The length of the provided array is not enough for long number storage:" +
                " length is " + bytes.length + ", but should be at least 8", exception.getMessage());

    }

    @Test
    @DisplayName("test Convert Byte Array To Long And Check Result")
    void testConvertByteArrayToLongAndCheckResult(){
        byte[] bytes = new byte[]{1, 0, 0, 0, 0, 0, 0, 0};
        long number = byteArrayToLong(bytes);
        assertEquals(1, number);
    }

    @Test
    @DisplayName("test Convert Byte Array To Double And Check Length")
    void testConvertByteArrayToDoubleAndCheckLength(){
        byte[] bytes = new byte[8];
        double number = byteArrayToDouble(bytes);
        assertEquals(0, number);
    }

    @Test
    @DisplayName("test Convert Byte Array To Int And Check Length")
    void testConvertByteArrayToIntAndCheckLength(){
        byte[] bytes = new byte[4];
        int number = byteArrayToInt(bytes);
        assertEquals(0, number);
    }

    @Test
    @DisplayName("test Convert Byte Array With Small Size To Int And Throw Exception")
    void testConvertByteArrayWithSmallSizeToIntAndThrowException(){
        byte[] bytes = new byte[3];
        ArrayIndexOutOfBoundsException exception = assertThrows(ArrayIndexOutOfBoundsException.class, () -> byteArrayToInt(bytes));
        assertEquals("The length of the provided array is not enough for int number storage:" +
                " length is " + bytes.length + ", but should be at least 4", exception.getMessage());

    }

    @Test
    @DisplayName("test Convert Byte Array To Int And Check Result")
    void testConvertByteArrayToIntAndCheckResult(){
        byte[] bytes = new byte[]{0, 0, 0, 1};
        int number = byteArrayToInt(bytes);
        assertEquals(1, number);
    }

}