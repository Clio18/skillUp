package com.obolonyk.skillup.io;

import java.io.IOException;
import java.io.InputStream;

public class ByteArrayInputStream extends InputStream {

    private int count = 0;
    private int index;
    private byte[] buffer;
    private boolean isClosed = false;
    private int wasRead = 0;

    public ByteArrayInputStream(byte[] buffer) {
        this.buffer = buffer;
    }

    @Override
    public int read() {
        if (count == buffer.length) {
            return -1;
        }
        return buffer[count++] & 0xFF;
    }

    @Override
    public int read(byte[] array) throws IOException {
        insureStreamIsNotClosed();
        return read(array, 0, array.length);
    }

    @Override
    public int read(byte[] array, int off, int len) throws IOException {
        insureStreamIsNotClosed();
        validateParameters(array, off, len);
        if (count == buffer.length) {
            return -1;
        }

        if (wasRead != 0) {
            int delta = buffer.length - wasRead;
            len = Math.min(delta, array.length);
        }

        System.arraycopy(buffer, index, array, off, len);
        index = index + len;
        wasRead = len;
        count = count + wasRead;
        return len;
    }

    @Override
    public void close() throws IOException {
        isClosed = true;
        super.close();
    }

    private void insureStreamIsNotClosed() throws IOException {
        if (isClosed) {
            throw new IOException("The input stream is not closed");
        }
    }

    private void validateParameters(byte[] array, int off, int length) {
        if (array == null) {
            throw new NullPointerException("array of bytes is null");
        } else if (off < 0 || length < 0 || length > array.length - off) {
            throw new IndexOutOfBoundsException("Position or length can`t be less than zero. Length can`t be more than " + (array.length - off));
        }
    }
}
