package com.obolonyk.skillup.io;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class BufferedInputStreamTest {

    @Test
    @DisplayName("Test Read By Bytes And Check Result")
    void testReadByBytesAndCheckResult() throws IOException {
        String content = "HelloXe";
        byte[] contentBytes = content.getBytes();
        InputStream inputStream = new ByteArrayInputStream(contentBytes);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        assertEquals('H', (char) bufferedInputStream.read());
        assertEquals('e', (char) bufferedInputStream.read());
        assertEquals('l', (char) bufferedInputStream.read());
        assertEquals('l', (char) bufferedInputStream.read());
        assertEquals('o', (char) bufferedInputStream.read());
        assertEquals('X', (char) bufferedInputStream.read());
        assertEquals('e', (char) bufferedInputStream.read());
        assertEquals(-1, bufferedInputStream.read());
    }

    @Test
    @DisplayName("Test Read By Array Of Bytes And Check Result")
    void testReadByArrayOfBytesAndCheckResult() throws IOException {
        String content = "Hello";
        byte[] contentBytes = content.getBytes();
        byte[] res = new byte[10];
        InputStream inputStream = new ByteArrayInputStream(contentBytes);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        assertEquals(0, bufferedInputStream.read(res, 0, 0));
        int countReadByte = bufferedInputStream.read(res, 0, 4);
        assertEquals(4, countReadByte);
        for (int i = 0; i < countReadByte; i++) {
            assertEquals(content.charAt(i), res[i]);
        }
        assertEquals(0, res[countReadByte]);
    }

    @Test
    @DisplayName("Test Read Empty String And Check Content")
    void testReadEmptyStringAndCheckContent() throws IOException {
        String content = "";
        InputStream inputStream = new ByteArrayInputStream(content.getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        assertEquals(-1, bufferedInputStream.read());
    }

    @Test
    @DisplayName("Test Read Array Of bytes And Check Content")
    void testReadArrayOfBytesAndCheckContent() throws IOException {
        String content = "HelloHelloHelloHello";
        InputStream inputStream = new ByteArrayInputStream(content.getBytes());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        byte[] bytes = new byte[content.length()];
        int i = bufferedInputStream.read(bytes);
        assertEquals(content.length(), i);
        assertEquals("HelloHelloHelloHello", new String(bytes));
    }

    @Test
    @DisplayName("Test Read By Array Of Bytes And Check Result")
    void testReadByArrayOfBytes() throws IOException {
        String content = "";
        byte[] contentBytes = content.getBytes();
        byte[] res = new byte[5];
        InputStream inputStream = new ByteArrayInputStream(contentBytes);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        int countReadByte = bufferedInputStream.read(res, 0, res.length);
        assertEquals(-1, countReadByte);
    }

    @Test
    void test() throws IOException {
        byte[] source = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new ByteArrayInputStream(source), 5);
        byte[] array = new byte[10];
        bufferedInputStream.read(array, 0, 2);
        bufferedInputStream.read(array, 2, 2);
        bufferedInputStream.read(array, 4, 2);
        bufferedInputStream.read(array, 6, 2);
        for (byte b : array) {
            System.out.println(b);
        }
    }

}