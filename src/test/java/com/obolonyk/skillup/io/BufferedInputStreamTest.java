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
    @DisplayName("Test Read By Array Of Bytes Multiply Times")
    void testReadByArrayOfBytesMultiplyTimes() throws IOException {
        byte[] source = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new ByteArrayInputStream(source), 5);
        byte[] array = new byte[10];
        bufferedInputStream.read(array, 0, 2);
        bufferedInputStream.read(array, 2, 2);
        bufferedInputStream.read(array, 4, 2);
        bufferedInputStream.read(array, 6, 2);
        bufferedInputStream.read(array, 8, 2);
        byte[] expected = new byte[]{1, 2, 3, 4, 5, 0, 6, 7, 8, 9};
        assertArrayEquals(expected, array);
    }

    @Test
    @DisplayName("Test Read By Array Of Bytes And Read Without Parameters")
    void testReadByArrayOfBytesAndReadWithoutParameters() throws IOException {
        byte[] source = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new ByteArrayInputStream(source), 5);
        byte[] array = new byte[10];
        int len = 5;
        int read = bufferedInputStream.read(array, 0, len);
        assertEquals(len, read);
        assertEquals(6, bufferedInputStream.read());
    }

    @Test
    @DisplayName("Test Read By Array When Len More Than Buffer Length")
    void testReadByArrayWhenLenMoreThanBufferLength() throws IOException {
        byte[] source = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new ByteArrayInputStream(source), 5);
        byte[] array = new byte[10];
        int len = 10;
        int read = bufferedInputStream.read(array, 0, len);
        assertEquals(len, read);
        assertArrayEquals(new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, array);
    }

    @Test
    @DisplayName("Test Read By Array When Len Less Than Elements In Buffer")
    void testReadByArrayWhenLenLessThanElementsInBuffer() throws IOException {
        byte[] source = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new ByteArrayInputStream(source), 5);
        byte[] array = new byte[10];
        int len = 2;
        int read = bufferedInputStream.read(array, 0, len);
        assertEquals(len, read);
        assertArrayEquals(new byte[]{1, 2, 0, 0, 0, 0, 0, 0, 0, 0}, array);
    }

    @Test
    @DisplayName("Test Read By Array When Len More Than Elements In Buffer")
    void testReadByArrayWhenLenMoreThanElementsInBuffer() throws IOException {
        byte[] source = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new ByteArrayInputStream(source), 5);
        byte[] array = new byte[10];
        int len = 6;
        int read = bufferedInputStream.read(array, 0, len);
        assertEquals(len, read);
        assertArrayEquals(new byte[]{1, 2, 3, 4, 5, 6, 0, 0, 0, 0}, array);
    }

    @Test
    @DisplayName("Test Read By Array On Empty Stream")
    void testReadByArrayOnEmptyStream() throws IOException {
        byte[] source = new byte[]{};
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new ByteArrayInputStream(source), 5);
        byte[] array = new byte[10];
        int read = bufferedInputStream.read(array, 0, 6);
        assertEquals(-1, read);
    }

    @Test
    @DisplayName("Test Read By Array When Desire Length For Read Is More Than Capacity Of Provided Array Than IndexOutOfBoundsException Throw")
    void testReadByArrayWhenDesireLengthForReadIsMoreThanCapacityOfProvidedArrayThanIndexOutOfBoundsExceptionThrow() {
        byte[] source = new byte[]{1, 2, 3, 4, 5};
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new ByteArrayInputStream(source), 5);
        byte[] array = new byte[10];
        int len = 11;
        int arrayLength = array.length;
        IndexOutOfBoundsException indexOutOfBoundsException = assertThrows(IndexOutOfBoundsException.class, () -> bufferedInputStream.read(array, 0, len));
        assertEquals("Len is to big for provided array: len is " + len + ", array length is: " + arrayLength,
                indexOutOfBoundsException.getMessage());
    }

    @Test
    @DisplayName("Test Read By Array When Desire Length For Read Is More Than Exist Capacity Than IndexOutOfBoundsException Throw")
    void testReadByArrayWhenDesireLengthForReadIsMoreThanExistCapacityThanIndexOutOfBoundsExceptionThrow() {
        byte[] source = new byte[]{1, 2, 3, 4, 5};
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new ByteArrayInputStream(source), 5);
        byte[] array = new byte[5];
        int len = 5;
        int off = 2;
        int arrayLength = array.length;
        IndexOutOfBoundsException indexOutOfBoundsException = assertThrows(IndexOutOfBoundsException.class, () -> bufferedInputStream.read(array, off, len));
        assertEquals("Len is to big for provided array and off: len is "
                        + len + ", capacity is array.length - off is: " + (arrayLength - off),
                indexOutOfBoundsException.getMessage());
    }

    @Test
    @DisplayName("Test Read By Array When Provided Len Parameter Less Than Zero Than IndexOutOfBoundsException Throw")
    void testReadByArrayWhenProvidedLenParameterLessThanZeroThanExistCapacityThanIndexOutOfBoundsExceptionThrow() {
        byte[] source = new byte[]{1, 2, 3, 4, 5};
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new ByteArrayInputStream(source), 5);
        byte[] array = new byte[5];
        int len = -5;
        int off = 2;
        IndexOutOfBoundsException indexOutOfBoundsException = assertThrows(IndexOutOfBoundsException.class, () -> bufferedInputStream.read(array, off, len));
        assertEquals("Len or off is less than zero: len is " + len + ", off is " + off,
                indexOutOfBoundsException.getMessage());
    }

    @Test
    @DisplayName("Test Read By Array When Provided Off Parameter Less Than Zero Than IndexOutOfBoundsException Throw")
    void testReadByArrayWhenProvidedOffParameterLessThanZeroThanExistCapacityThanIndexOutOfBoundsExceptionThrow() {
        byte[] source = new byte[]{1, 2, 3, 4, 5};
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new ByteArrayInputStream(source), 5);
        byte[] array = new byte[5];
        int len = 5;
        int off = -2;
        IndexOutOfBoundsException indexOutOfBoundsException = assertThrows(IndexOutOfBoundsException.class, () -> bufferedInputStream.read(array, off, len));
        assertEquals("Len or off is less than zero: len is " + len + ", off is " + off,
                indexOutOfBoundsException.getMessage());
    }

    @Test
    @DisplayName("Test Read After Stream Is Closed")
    void testReadAfterStreamIsClosed() throws IOException {
        byte[] source = new byte[]{1, 2, 3, 4, 5};
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new ByteArrayInputStream(source), 5);
        int read = bufferedInputStream.read();
        assertEquals(1, read);
        bufferedInputStream.close();

        IOException exception = assertThrows(IOException.class, bufferedInputStream::read);
        assertEquals("Stream is closed!", exception.getMessage());
    }

    @Test
    @DisplayName("Test Read When Stream Is Null")
    void testReadWhenStreamIsNull() {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(null, 5);
        IOException exception = assertThrows(IOException.class, bufferedInputStream::read);
        assertEquals("Stream is closed!", exception.getMessage());
    }
}