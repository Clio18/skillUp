package com.obolonyk.skillup.io;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ByteArrayOutputStreamTest {

    @Test
    @DisplayName("Test Write Array Of Bytes And Check Result Part")
    void testWriteArrayOfBytesAndCheckResultPart() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] array = {1, 2, 3, 4, 5};
        byteArrayOutputStream.write(array, 0, 3);
        byte[] expected = {1, 2, 3};
        byte[] actual = byteArrayOutputStream.toByteArray();
        assertArrayEquals(expected, actual);
    }

    @Test
    @DisplayName("Test Write Byte And Check Result(")
    void testWriteByteAndCheckResult() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte expected = 32;
        byteArrayOutputStream.write(expected);
        byte[] actual = byteArrayOutputStream.toByteArray();
        assertEquals(expected, actual[0]);
    }

    @Test
    @DisplayName("Test Write Array Of Bytes And Check Result(")
    void testWriteArrayOfBytesAndCheckResult() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] expected = {1, 2, 3, 4, 5};
        byteArrayOutputStream.write(expected);
        byte[] actual = byteArrayOutputStream.toByteArray();
        assertArrayEquals(expected, actual);
    }
}