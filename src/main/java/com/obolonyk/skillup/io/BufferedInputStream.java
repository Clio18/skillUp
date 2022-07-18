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
        parameterValidation(array, off, len);
        checkIfClosed();
        int elementsInBuffer = count - position;
        if (elementsInBuffer <= 0) {
            if (len >= buffer.length) {
                checkIfClosed();
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
        int elementsWereRead = 0;
        while (true) {
            int read = readHelper(array, off + elementsWereRead, len - elementsWereRead);
            if (read == 0) {
                return 0;
            }
            if (read == -1) {
                return -1;
            }
            elementsWereRead += read;
            return elementsWereRead;
        }
    }

    @Override
    public void close() throws IOException {
        target.close();
        buffer = null;
    }

    @Override
    public int read() throws IOException {
        checkIfClosed();
        int av = count - position;
        if (av <= 0 && (count == 0 || count == buffer.length)) {
            fillBuffer();
        }
        if (position != buffer.length && count != -1 && count != position) {
            byte current = buffer[position];
            position++;
            return current & 0xFF;
        } else {
            return -1;
        }
    }

    private void fillBuffer() throws IOException {
        position = 0;
        checkIfClosed();
        int elementsWereRead = target.read(buffer, position, buffer.length - position);
        if (elementsWereRead > 0) {
            count = elementsWereRead + position;
        }
    }

    private void checkIfClosed() throws IOException {
        if (target == null || buffer == null) {
            throw new IOException("Stream is closed!");
        }
    }

    private void parameterValidation (byte[] array, int off, int len){
        if (len > array.length) {
            throw new IndexOutOfBoundsException("Len is to big for provided array: len is "
                    + len + ", array length is: " + array.length);
        }
        if (array.length - off < len) {
            throw new IndexOutOfBoundsException("Len is to big for provided array and off: len is "
                    + len + ", capacity is array.length - off is: " + (array.length - off));
        }
        if ((off | len) < 0) {
            throw new IndexOutOfBoundsException("Len or off is less than zero: len is " + len + ", off is " + off);
        }
    }
}
