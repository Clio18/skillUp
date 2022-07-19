package com.obolonyk.skillup.io;

import java.io.IOException;
import java.io.OutputStream;

public class BufferedOutputStream extends OutputStream {

    private OutputStream target;
    private byte[] buffer;
    private static final int CAPACITY = 5;
    private int count;


    public BufferedOutputStream(OutputStream target) {
        this.target = target;
        buffer = new byte[CAPACITY];
    }

    @Override
    public void write(int b) throws IOException {
        if (count == CAPACITY) {
            writeBuffer();
        }
        buffer[count++] = (byte) b;
    }

    @Override
    public void write(byte[] bytes) throws IOException {
        write(bytes, 0, bytes.length);
    }

    @Override
    public void write(byte[] array, int off, int len) throws IOException {
        if (len > buffer.length) {
            if (count > 0) {
                target.write(buffer, 0, count);
                count = 0;
            }
            target.write(array, off, len);
            return;
        }
        int space = buffer.length - len;
        if (len > space) {
            if (count > 0) {
                target.write(buffer, 0, count);
                count = 0;
            }
        }
        System.arraycopy(array, off, buffer, count, len);
        count = count + len;
    }

    @Override
    public void flush() throws IOException {
        if (buffer == null) {
            throw new IOException("Stream is closed");
        }
        if (count > 0) {
            target.write(buffer, 0, count);
            count = 0;
        }
        target.flush();
    }

    @Override
    public void close() throws IOException {
        if (count > 0) {
            target.write(buffer, 0, count);
            count = 0;
        }
        target.close();
    }

    private void writeBuffer() throws IOException {
        if (count > 0) {
            target.write(buffer, 0, count);
            count = 0;
        }
    }
}
