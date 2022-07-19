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
        if (currentPosition == buffer.length){
            byte [] newBuff = new byte[buffer.length + 1];
            System.arraycopy(buffer, 0, newBuff, 0, buffer.length);
            buffer = newBuff;
        }
        buffer[currentPosition] = (byte) b;
        currentPosition++;
    }

    @Override
    public void write(byte[] array) throws IOException {
        write(array, 0, array.length);
    }

    @Override
    public void write(byte[] array, int off, int len) throws IOException {
        validateParameters(array, off, len);
        if (len>buffer.length){
            byte [] newBuff = new byte[len];
            System.arraycopy(buffer, 0, newBuff, 0, buffer.length);
            buffer = newBuff;
        }
        int spaceInBuffer = buffer.length - currentPosition;
        if (spaceInBuffer<len){
            byte [] newBuff = new byte[buffer.length + len];
            System.arraycopy(buffer, 0, newBuff, 0, buffer.length);
            buffer = newBuff;
        }
        System.arraycopy(array, 0, buffer, currentPosition, len);
        currentPosition = currentPosition + len;
    }

    @Override
    public void close() {

    }

    public byte[] toByteArray() {
        return Arrays.copyOf(buffer, currentPosition);
    }

    static void validateParameters(byte[] array, int off, int length) {
        if (array == null) {
            throw new NullPointerException("Provided array is null");
        } else if (off < 0 || length < 0) {
            throw new IndexOutOfBoundsException("Position or length can`t be less than zero: off is "
            + off + ", len is " + length);
        }
    }
}
