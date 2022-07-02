package com.obolonyk.skillup.io;

import java.io.IOException;
import java.io.InputStream;

public class BufferedInputStream extends InputStream {

    private InputStream target;
    private byte[] buffer;
    private static final int CAPACITY = 5;
    private int position;
    private int check;


    public BufferedInputStream(InputStream target, int capacity) throws IOException {
        this.target = target;
        this.buffer = new byte[capacity];
        check = fillBuffer();
    }

    public BufferedInputStream(InputStream target) throws IOException {
        this(target, CAPACITY);
    }

    @Override
    public int read(byte[] array) throws IOException {
        return read(array, 0, array.length);
    }

    @Override
    public int read(byte[] array, int off, int len) throws IOException {
        int counter = 0;
        if (buffer.length - position > len) {

            for (int i = 0; i < len; i++) {
                if (position == buffer.length) {
                    position = 0;
                    check = fillBuffer();
                }
                if (check == position) {
                    return -1;
                }
                if (buffer[position] == 0) {
                    return counter;
                }
                array[i] = buffer[position];
                position++;
                counter++;
            }

        }
        return counter;
    }

    @Override
    public void close() throws IOException {
        target.close();
        buffer = null;
    }

    @Override
    public int read() throws IOException {

        if (position == buffer.length) {
            position = 0;
            check = fillBuffer();
        }

        if (position != buffer.length && check != position) {
            byte current = buffer[position];
            position++;
            return current;
        } else {
            return -1;
        }
    }

    private int fillBuffer() throws IOException {
        int readBytes = target.read(buffer);
        if ((buffer.length > readBytes) && (readBytes > 0)) {
            System.arraycopy(buffer, 0, buffer, 0, readBytes);
            for (int i = readBytes; i < CAPACITY; i++) {
                buffer[i] = 0;
            }
        }
        return readBytes;
    }
}
