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
        check = readBytesToBuffer(target, buffer);
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
        if (check == -1) {
            return -1;
        }
        int counter = 0;
        for (int i = 0; i < len; i++) {
            fillBuffer();
            if (buffer[position] == 0) {
                return counter;
            }
            array[i] = buffer[position];
            position++;
            counter++;
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
        fillBuffer();
        if (position != buffer.length && check != position && check != -1) {
            byte current = buffer[position];
            position++;
            return current & 0xFF;
        } else {
            return -1;
        }
    }

    static int readBytesToBuffer(InputStream target, byte[] buffer) throws IOException {
        int readBytes = target.read(buffer);
        if (readBytes != -1) {
            for (int i = readBytes; i < buffer.length; i++) {
                buffer[i] = 0;
            }
        }
        return readBytes;
    }

    private void fillBuffer() throws IOException {
        if (position == buffer.length) {
            position = 0;
            check = readBytesToBuffer(target, buffer);
        }
    }
}
