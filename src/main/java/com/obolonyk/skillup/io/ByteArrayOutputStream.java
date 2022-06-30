package com.obolonyk.skillup.io;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class ByteArrayOutputStream extends OutputStream {

    private int currentPosition;
    private byte[] buffer;

    public ByteArrayOutputStream() {
        this.buffer = new byte[32];
    }

    public ByteArrayOutputStream(int size) {
        this.buffer = new byte[size];
    }

    @Override
    public void write(int b) {
        buffer [currentPosition] = (byte) b;
        currentPosition++;
    }

    @Override
    public void write(byte[] array) {
        write(array, 0, array.length);
    }

    @Override
    public void write(byte[] array, int off, int len) {
        System.arraycopy(array, 0, buffer, off, len);
        currentPosition = currentPosition + len;
    }

    @Override
    public void flush() throws IOException {
        super.flush();
    }

    @Override
    public void close() throws IOException {
        super.close();
    }

    public byte[] toByteArray() {
        return Arrays.copyOf(buffer, currentPosition);
    }
}
