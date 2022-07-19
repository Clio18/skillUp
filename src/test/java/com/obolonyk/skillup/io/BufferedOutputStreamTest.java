package com.obolonyk.skillup.io;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class BufferedOutputStreamTest {


    @Test
    @DisplayName("Test When Len More Than Buffer Length")
    void testWhenLenMoreThanBufferLength() throws IOException {
        byte[] source = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(10);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
        //buffer size (5) < len (15)
        bufferedOutputStream.write(source, 0, 15);
        bufferedOutputStream.flush();
        byte[] expected = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        assertArrayEquals(expected, byteArrayOutputStream.toByteArray());
    }

    @Test
    @DisplayName("Test When Len More Than Buffer Capacity")
    void testWhenLenMoreThanBufferCapacity() throws IOException {
        byte[] source = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(10);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
        //buffer size is 5, buffer capacity: 5-3 = 2
        bufferedOutputStream.write(source, 0, 3);
        // len (3) > buffer capacity (2)
        bufferedOutputStream.write(source, 3, 3);
        bufferedOutputStream.flush();
        byte[] expected = new byte[]{1, 2, 3, 4, 5, 6};
        assertArrayEquals(expected, byteArrayOutputStream.toByteArray());
    }

    @Test
    @DisplayName("Test When Len Less Than Buffer Capacity")
    void testWhenLenLessThanBufferCapacity() throws IOException {
        byte[] source = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(10);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
        //buffer size is 5, buffer capacity: 5-3 = 2
        bufferedOutputStream.write(source, 0, 3);
        // len (1) < buffer capacity (2)
        bufferedOutputStream.write(source, 3, 1);
        bufferedOutputStream.flush();
        byte[] expected = new byte[]{1, 2, 3, 4};
        assertArrayEquals(expected, byteArrayOutputStream.toByteArray());
    }

    @Test
    @DisplayName("Test When Len Less Than Buffer Capacity")
    void testWhenLenLessOrEqualBufferSizeAndDoNotDoFlash() throws IOException {
        byte[] source = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(10);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
        bufferedOutputStream.write(source, 0, 5);
        byte[] expected = new byte[0];
        assertArrayEquals(expected, byteArrayOutputStream.toByteArray());
    }

    @Test
    @DisplayName("Test When Len Less Than Buffer Capacity")
    void testWhenLenMoreBufferSizeAndDoNotDoFlash() throws IOException {
        byte[] source = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(10);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
        bufferedOutputStream.write(source, 0, 6);
        byte[] expected = new byte[]{1, 2, 3, 4, 5, 6};
        assertArrayEquals(expected, byteArrayOutputStream.toByteArray());
    }

    @Test
    @DisplayName("Test When Call Write After Close And Return Result")
    void testWhenCallWriteAfterCloseAndReturnResult() throws IOException {
        byte[] source = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(10);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
        bufferedOutputStream.close();
        bufferedOutputStream.write(source, 0, 6);
        byte[] expected = new byte[]{1, 2, 3, 4, 5, 6};
        assertArrayEquals(expected, byteArrayOutputStream.toByteArray());
    }



}