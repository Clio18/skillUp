package com.obolonyk.skillup.io;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ByteArrayInputStreamTest {

    @Test
    @DisplayName("test Read And Check Content")
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
    @DisplayName("test Read Empty String And Check Content")
    void testReadEmptyStringAndCheckContent() {
        String content = "";
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content.getBytes());
        assertEquals(-1, byteArrayInputStream.read());
    }

    @Test
    @DisplayName("test Read Array Of bytes And Check Content")
    void testReadArrayOfBytesAndCheckContent() {
        String content = "Hello";
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content.getBytes());
        byte[] bytes = new byte[content.length()];
        int i = byteArrayInputStream.read(bytes);
        assertEquals(content.length(), i);
        assertEquals("Hello", new String(bytes));
    }

    @Test
    @DisplayName("test Read Array Of bytes And Check Content Part")
    void testReadArrayOfBytesAndCheckContentPart() {
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

}