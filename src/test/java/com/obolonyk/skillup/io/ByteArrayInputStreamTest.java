package com.obolonyk.skillup.io;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ByteArrayInputStreamTest {

    @Test
    @DisplayName("Test Read And Check Content")
    void testReadAndCheckContent() {
        String content = "Hello";
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content.getBytes());
        assertEquals('H', (char) byteArrayInputStream.read());
        assertEquals('e', (char) byteArrayInputStream.read());
        assertEquals('l', (char) byteArrayInputStream.read());
        assertEquals('l', (char) byteArrayInputStream.read());
        assertEquals('o', (char) byteArrayInputStream.read());
        assertEquals(-1, byteArrayInputStream.read());
    }

    @Test
    @DisplayName("Test Read Empty String And Check Content")
    void testReadEmptyStringAndCheckContent() {
        String content = "";
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content.getBytes());
        assertEquals(-1, byteArrayInputStream.read());
    }

    @Test
    @DisplayName("Test Read Array Of bytes And Check Content")
    void testReadArrayOfBytesAndCheckContent() throws IOException {
        String content = "Hello";
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content.getBytes());
        byte[] bytes = new byte[content.length()];
        int i = byteArrayInputStream.read(bytes);
        assertEquals(content.length(), i);
        assertEquals("Hello", new String(bytes));
    }

    @Test
    @DisplayName("Test Read Array Of bytes And Check Content Part")
    void testReadArrayOfBytesAndCheckContentPart() throws IOException {
        String content = "Hello";
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content.getBytes());
        byte[] bytes = new byte[content.length()];
        String expected = "He";
        int i = byteArrayInputStream.read(bytes, 1, 2);
        assertEquals(expected.length(), i);
        assertEquals(expected, new String(bytes).trim());
        assertEquals(0, bytes[0]);
        assertNotEquals(0, bytes[1]);
        assertNotEquals(0, bytes[2]);
        assertEquals(0, bytes[3]);
        assertEquals(0, bytes[4]);
    }

    @Test
    @DisplayName("Test ValidateParameters When Array Is Null And Check Exception Message")
    void testValidateParametersWhenArrayIsNullAndCheckExceptionMessage() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            ByteArrayInputStream.validateParameters(null, 0, 0);
        });
        assertEquals("Provided array is null", exception.getMessage());
    }

    @Test
    @DisplayName("Test ValidateParameters When Off Parameter Less Than Zero And Check Exception Message")
    void testValidateParametersWhenOffParameterLessThanZeroAndCheckExceptionMessage() {
        byte[] array = new byte[5];
        int off = -1;
        IndexOutOfBoundsException exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            ByteArrayInputStream.validateParameters(array, off, 0);
        });
        assertEquals("Position or length can`t be less than zero. Capacity in buffer is not enough: length is "
                + 0 + " and capacity is " + (array.length - off), exception.getMessage());
    }

    @Test
    @DisplayName("Test ValidateParameters When Length Parameter Less Than Zero And Check Exception Message")
    void testValidateParametersWhenLengthParameterLessThanZeroAndCheckExceptionMessage() {
        byte[] array = new byte[5];
        int off = 2;
        int length = -1;
        IndexOutOfBoundsException exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            ByteArrayInputStream.validateParameters(array, off, length);
        });
        assertEquals("Position or length can`t be less than zero. Capacity in buffer is not enough: length is "
                + length + " and capacity is " + (array.length - off), exception.getMessage());
    }

    @Test
    @DisplayName("Test ValidateParameters When Length Parameter More Than Array Length And Check Exception Message")
    void testValidateParametersWhenLengthParameterMoreThanArrayLengthAndCheckExceptionMessage() {
        byte[] array = new byte[5];
        int off = 2;
        int length = 10;
        IndexOutOfBoundsException exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            ByteArrayInputStream.validateParameters(array, off, length);
        });
        assertEquals("Position or length can`t be less than zero. Capacity in buffer is not enough: length is "
                + length + " and capacity is " + (array.length - off), exception.getMessage());
    }

    @Test
    @DisplayName("Test InsureStreamIsNotClosed On Closed Stream And Check Exception Message")
    void testInsureStreamIsNotClosedOnClosedStreamAndCheckExceptionMessage() throws IOException {
        String content = "Hello";
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content.getBytes());
        byteArrayInputStream.close();
        IOException exception = assertThrows(IOException.class, () -> {
            byteArrayInputStream.insureStreamIsNotClosed();
        });
        assertEquals("The input stream is not closed", exception.getMessage());
    }

    @Test
    @DisplayName("Test Raed After Close And Check Result")
    void testRaedAfterCloseAndCheckResult() throws IOException {
        byte[] source = new byte[]{1, 2, 3};
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(source);
        assertEquals(1, byteArrayInputStream.read());
        byteArrayInputStream.close();
        assertEquals(2, byteArrayInputStream.read());
        assertEquals(3, byteArrayInputStream.read());
        assertEquals(-1, byteArrayInputStream.read());
    }

    @Test
    @DisplayName("Test Read By Array For All Elements")
    void testReadByArrayForAllElements() throws IOException {
        byte[] source = new byte[]{1, 2, 3, 4, 5};
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(source);
        byte[] array = new byte[5];
        byteArrayInputStream.read(array, 0, 5);
        assertArrayEquals(source, array);
    }

    @Test
    @DisplayName("Test Read By Array Multiply Times")
    void testReadByArrayMultiplyTimes() throws IOException {
        byte[] source = new byte[]{1, 2, 3, 4, 5};
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(source);
        byte[] array = new byte[5];
        byteArrayInputStream.read(array, 0, 2);
        byteArrayInputStream.read(array, 2, 2);
        byteArrayInputStream.read(array, 4, 1);
        assertArrayEquals(source, array);
    }

    @Test
    @DisplayName("Test Read By Array Multiply Times And ThanRead")
    void testReadByArrayMultiplyTimesAndThanRead() throws IOException {
        byte[] source = new byte[]{1, 2, 3, 4, 5};
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(source);
        byte[] array = new byte[5];
        byteArrayInputStream.read(array, 0, 2);
        byteArrayInputStream.read(array, 2, 2);
        int read = byteArrayInputStream.read();
        assertEquals(5, read);
    }

    @Test
    @DisplayName("Test Read By Array On Empty Stream")
    void testReadByArrayOnEmptyStream() throws IOException {
        byte[] source = new byte[]{};
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(source);
        byte[] array = new byte[5];
        int read = byteArrayInputStream.read(array, 0, 2);
        assertEquals(-1, read);
    }

    @Test
    @DisplayName("Test Read On Empty Stream")
    void testReadOnEmptyStream() {
        byte[] source = new byte[]{};
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(source);
        int read = byteArrayInputStream.read();
        assertEquals(-1, read);
    }

}