package com.obolonyk.skillup.io;

import java.io.IOException;
import java.io.InputStream;

public class ByteArrayInputStream extends InputStream {

    private int currentPosition;
    private byte[] buffer;

    public ByteArrayInputStream(byte[] buffer) {
        this.buffer = buffer;
    }

    @Override
    public int read() {
        if (currentPosition == buffer.length) {
            return -1;
        }
        return buffer[currentPosition++] & 0xFF;
    }

    @Override
    public int read(byte[] array) {
        return read(array, 0, array.length);
    }

    @Override
    public int read(byte[] array, int off, int len) {
        while (read() != -1) {
            System.arraycopy(buffer, 0, array, off, len);
        }
        return len;
    }

    @Override
    public void close() throws IOException {
        super.close();
    }
}
