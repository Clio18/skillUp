package com.obolonyk.skillup.io;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class ByteArrayOutputStreamTest {

    @Test
    @DisplayName("Test Write Array Of Bytes And Check Result Part")
    void testWriteArrayOfBytesAndCheckResultPart() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] array = {1, 2, 3, 4, 5};
        byteArrayOutputStream.write(array, 0, 3);
        byte[] expected = {1, 2, 3};
        byte[] actual = byteArrayOutputStream.toByteArray();
        assertArrayEquals(expected, actual);
    }

    @Test
    @DisplayName("Test Write Byte And Check Result")
    void testWriteByteAndCheckResult() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte expected = 32;
        byteArrayOutputStream.write(expected);
        byte[] actual = byteArrayOutputStream.toByteArray();
        assertEquals(expected, actual[0]);
    }

    @Test
    @DisplayName("Test Write Array Of Bytes And Check Result")
    void testWriteArrayOfBytesAndCheckResult() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] expected = {1, 2, 3, 4, 5};
        byteArrayOutputStream.write(expected);
        byte[] actual = byteArrayOutputStream.toByteArray();
        assertArrayEquals(expected, actual);
    }

    @Test
    @DisplayName("Test When Len More Than Buffer Size")
    void testWhenLenMoreThanBufferSize() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(10);
        byte[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        byteArrayOutputStream.write(expected);
        byte[] actual = byteArrayOutputStream.toByteArray();
        assertArrayEquals(expected, actual);
    }

    @Test
    @DisplayName("Test When Write And Len More Than Buffer Size")
    void testWhenWriteAndLenMoreThanBufferSize() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(10);
        byte[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        byteArrayOutputStream.write(array);
        byteArrayOutputStream.write(100);

        byte[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 100};
        byte[] actual = byteArrayOutputStream.toByteArray();
        assertArrayEquals(expected, actual);
    }

    @Test
    @DisplayName("Test When Space In Buffer Not Enough")
    void testWhenSpaceInBufferNotEnough() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(10);
        byte[] array1 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        byteArrayOutputStream.write(array1);
        byte[] array2 = {10, 11, 12, 13, 14, 15};
        byteArrayOutputStream.write(array2);

        byte[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        byte[] actual = byteArrayOutputStream.toByteArray();
        assertArrayEquals(expected, actual);
    }

    @Test
    @DisplayName("Test When Provided Array Is Null Throw Exception")
    void testWhenProvidedArrayIsNullThrowException()  {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(10);
        NullPointerException nullPointerException = assertThrows(NullPointerException.class, () -> byteArrayOutputStream.write(null, 0, 2));
        assertEquals("Provided array is null", nullPointerException.getMessage());
    }

    @Test
    @DisplayName("Test When Provided Off Parameter Is Less Than Zero Throw Exception")
    void testWhenProvidedOffParameterIsLessThanZeroThrowException() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(10);
        int off = -1;
        int length = 2;
        IndexOutOfBoundsException indexOutOfBoundsException = assertThrows(IndexOutOfBoundsException.class, () -> byteArrayOutputStream.write(new byte[5], off, length));
        assertEquals("Position or length can`t be less than zero: off is " +
                 + off + ", len is " + length, indexOutOfBoundsException.getMessage());
    }

    @Test
    @DisplayName("Test When Provided Len Parameter Is Less Than Zero Throw Exception")
    void testWhenProvidedLenParameterIsLessThanZeroThrowException() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(10);
        int off = 1;
        int length = -2;
        IndexOutOfBoundsException indexOutOfBoundsException = assertThrows(IndexOutOfBoundsException.class, () -> byteArrayOutputStream.write(new byte[5], off, length));
        assertEquals("Position or length can`t be less than zero: off is " +
                + off + ", len is " + length, indexOutOfBoundsException.getMessage());
    }

    @Test
    @DisplayName("Test When Call Write After Close And Return Result")
    void testWhenCallWriteAfterCloseAndReturnResult() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(10);
        byte[] array1 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        byteArrayOutputStream.close();
        byteArrayOutputStream.write(array1);
        byte[] actual = byteArrayOutputStream.toByteArray();
        assertArrayEquals(array1, actual);
    }
}