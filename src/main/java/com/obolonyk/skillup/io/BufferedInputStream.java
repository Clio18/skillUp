package com.obolonyk.skillup.io;

import java.io.IOException;
import java.io.InputStream;

public class BufferedInputStream extends InputStream {

    private InputStream target;
    private byte[] buffer;
    private static final int CAPACITY = 5;
    private int position;
    private int count;


    public BufferedInputStream(InputStream target, int capacity) {
        this.target = target;
        this.buffer = new byte[capacity];
    }

    public BufferedInputStream(InputStream target) {
        this(target, CAPACITY);
    }


    public int readHelper(byte[] array, int off, int len) throws IOException {
        if (count == -1) {
            return -1;
        }
        if (len > array.length) {
            throw new IndexOutOfBoundsException("Len is to big for provided array");
        }
        if (array.length - off < len) {
            throw new IndexOutOfBoundsException("Len is to big for provided array");
        }
        if ((off | len) < 0) {
            throw new IndexOutOfBoundsException("Len or off is less than zero");
        }

        int elementsInBuffer = count - position;

        if (elementsInBuffer <= 0) {
            if (len >= buffer.length) {
                return target.read(array, off, len);
            }
            fillBuffer();
        }

        elementsInBuffer = count - position;

        if (elementsInBuffer <= 0) {
            return -1;
        }

        if (elementsInBuffer < len) {
            System.arraycopy(buffer, position, array, off, elementsInBuffer);
            position = position + elementsInBuffer;
            return elementsInBuffer;
        } else {
            System.arraycopy(buffer, position, array, off, len);
            position = position + len;
            return len;
        }
    }

    @Override
    public int read(byte[] array, int off, int len) throws IOException {
        int n = 0;
        while (true) {
            int nread = readHelper(array, off + n, len - n);
            if (nread == 0) {
                return 0;
            }
            if (nread == -1) {
                return -1;
            }
            n += nread;
            return n;
        }
    }

    private void fillBuffer() throws IOException {
        position = 0;
        int n = target.read(buffer, position, buffer.length - position);
        if (n > 0) {
            count = n + position;
        }
    }

    @Override
    public void close() throws IOException {
        target.close();
        buffer = null;
    }

    @Override
    public int read() throws IOException {
        if (position>= count){
            fillBuffer();
            if(position>=count){
                return -1;
            }
        }
        if (position != buffer.length && count != -1) {
            byte current = buffer[position];
            position++;
            return current & 0xFF;
        } else {
            return -1;
        }
    }

    public static void main(String[] args) throws IOException {
        byte[] source = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        java.io.BufferedInputStream bufferedInputStream = new java.io.BufferedInputStream(new ByteArrayInputStream(source), 5);
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
