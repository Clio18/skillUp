package com.obolonyk.skillup.io;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class BufferedOutputStreamTest {

    @Test
    @DisplayName("Test Write Array Of Bytes And Check Result Part")
    void testWriteArrayOfBytesAndCheckResultPart() throws IOException {
        OutputStream outputStream = new FileOutputStream("text3.txt");
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        bufferedOutputStream.write('H');
        bufferedOutputStream.write('e');
        bufferedOutputStream.write('l');
        bufferedOutputStream.write('l');
        bufferedOutputStream.write('o');
        bufferedOutputStream.flush();
        FileInputStream fileInputStream = new FileInputStream("text3.txt");
        assertEquals('H', fileInputStream.read());
        assertEquals('e', fileInputStream.read());
        assertEquals('l', fileInputStream.read());
        assertEquals('l', fileInputStream.read());
        assertEquals('o', fileInputStream.read());
        assertEquals(-1, fileInputStream.read());
        File file = new File("text3.txt");
        file.delete();
    }

    @Test
    @DisplayName("Test Write method with parameters")
    void testWriteWithParam() throws IOException {
        OutputStream outputStream = new FileOutputStream("text3.txt");
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        bufferedOutputStream.write("Hello".getBytes());
        bufferedOutputStream.flush();
        FileInputStream fileInputStream = new FileInputStream("text3.txt");
        assertEquals('H', fileInputStream.read());
        assertEquals('e', fileInputStream.read());
        assertEquals('l', fileInputStream.read());
        assertEquals('l', fileInputStream.read());
        assertEquals('o', fileInputStream.read());
        assertEquals(-1, fileInputStream.read());
        File file = new File("text3.txt");
        file.delete();
    }


}