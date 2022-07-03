package com.obolonyk.skillup.io;

import java.io.IOException;
import java.io.OutputStream;

public class BufferedOutputStream extends OutputStream {

    private OutputStream target;
    private byte[] buffer;
    private static final int CAPACITY = 5;
    private int position;


    public BufferedOutputStream(OutputStream target) {
        this.target = target;
        buffer = new byte[CAPACITY];
    }


    @Override
    public void write(int b) throws IOException {
        if (position == CAPACITY) {
            writeBuffer();
        }
        buffer[position++] = (byte) b;
    }

    @Override
    public void write(byte[] bytes) throws IOException {
        write(bytes, 0, bytes.length);
    }

    @Override
    public void write(byte[] bytes, int off, int len) throws IOException {
        System.arraycopy(bytes, off, buffer, position, len);
        position = position + len;
    }

    @Override
    public void flush() throws IOException {
        if (buffer == null) {
            throw new IOException("Stream is closed");
        }
        writeBuffer();
        target.flush();
    }

    @Override
    public void close() throws IOException {
        target.close();
    }

    private void writeBuffer() throws IOException {
        if (position > 0) {
            target.write(buffer, 0, position);
            position = 0;
        }
    }
}
