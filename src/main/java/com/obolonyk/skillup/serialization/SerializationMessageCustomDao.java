package com.obolonyk.skillup.serialization;

import java.io.*;
import java.math.BigInteger;
import java.util.Date;


public class SerializationMessageCustomDao implements MessageDao {
    private static final int LONG_AND_DOUBLE_BYTE_SIZE = 8;
    private static final int INT_BYTE_SIZE = 4;
    private ByteArrayOutputStream byteArrayOutputStream;
    private int position = 0;

    /*
     * The method which takes object of the Message class,
     * converts each its fields to the byte arrays
     *  and writes them to the bufferedOutputStream.
     *  The way of writing the fields is next:
     *  date (long which takes 8 bytes) ->
     *  amount (double which takes 8 bytes) ->
     *  message length (which is the length of the byte array of the text,
     *  int which takes 4 bytes) ->
     *  message (which is the byte array of the text)
     * */
    @Override
    public void save(Message message) throws IOException {
        // DATE
        long time = message.getDate().getTime();
        byte[] bytesForTime = longToByteArray(time);
        // AMOUNT
        double amount = message.getAmount();
        byte[] bytesForAmount = doubleToByteArray(amount);
        // MESSAGE
        String text = message.getMessage();
        // MESSAGE TEXT IN BYTES ARRAY
        byte[] bytesForMessage = text.getBytes();
        // LENGTH OF THE MESSAGE TEXT IN BYTES ARRAY
        int lengthOfText = bytesForMessage.length;
        byte[] bytesForLengthOfText = intToByteArray(lengthOfText);

        byteArrayOutputStream = new ByteArrayOutputStream();
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream)) {
            bufferedOutputStream.write(bytesForTime);
            bufferedOutputStream.write(bytesForAmount);
            bufferedOutputStream.write(bytesForLengthOfText);
            bufferedOutputStream.write(bytesForMessage);
        }
    }

    /*
     * The method which should return the Message object.
     *  The way of reading the fields is next:
     *  date (from 0 to 8 bytes) ->
     *  amount (from 8 to 16 bytes) ->
     *  message length (from 16 to 20 bytes) ->
     *  message (from 20 to 20 + message length bytes)
     * */
    @Override
    public Message load() throws IOException {
        byte[] bytes = byteArrayOutputStream.toByteArray();
        byte[] data;
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(byteArrayInputStream)) {
            // ALL DATA
            data = bufferedInputStream.readAllBytes();
        }

        //DATE: 0 to 8 bytes
        byte[] dateInBytes = new byte[LONG_AND_DOUBLE_BYTE_SIZE];
        System.arraycopy(data, position, dateInBytes, 0, LONG_AND_DOUBLE_BYTE_SIZE);
        position += LONG_AND_DOUBLE_BYTE_SIZE;
        long toLong = byteArrayToLong(dateInBytes);
        Date dateForMessage = new Date(toLong);

        //AMOUNT: 8 to 16 bytes
        byte[] amountInBytes = new byte[LONG_AND_DOUBLE_BYTE_SIZE];
        System.arraycopy(data, position, amountInBytes, 0, LONG_AND_DOUBLE_BYTE_SIZE);
        position += LONG_AND_DOUBLE_BYTE_SIZE;
        double amountForMessage = byteArrayToDouble(amountInBytes);

        //MESSAGE TEXT LENGTH: 16 to 20 bytes
        byte[] lengthOfTheText = new byte[INT_BYTE_SIZE];
        System.arraycopy(data, position, lengthOfTheText, 0, INT_BYTE_SIZE);
        position += INT_BYTE_SIZE;
        //MESSAGE TEXT ITSELF: 20 to TEXT LENGTH bytes
        int textLength = byteArrayToInt(lengthOfTheText);
        byte[] textInBytes = new byte[textLength];
        System.arraycopy(data, position, textInBytes, 0, textLength);
        String textForMessage = new String(textInBytes);
        position += textLength;

        return new Message(dateForMessage, textForMessage, amountForMessage);
    }

    static byte[] longToByteArray(long lng) {
        return new byte[]{
                (byte) lng,
                (byte) (lng >> 8),
                (byte) (lng >> 16),
                (byte) (lng >> 24),
                (byte) (lng >> 32),
                (byte) (lng >> 40),
                (byte) (lng >> 48),
                (byte) (lng >> 56)};
    }

    static long byteArrayToLong(byte[] bytes) {
        if (bytes.length<8){
            throw new ArrayIndexOutOfBoundsException ("The length of the provided array is not" +
                    " enough for long number storage: length is " + bytes.length + ", but should be at least 8");
        }
        return ((long) bytes[7] << 56)
                | ((long) bytes[6] & 0xff) << 48
                | ((long) bytes[5] & 0xff) << 40
                | ((long) bytes[4] & 0xff) << 32
                | ((long) bytes[3] & 0xff) << 24
                | ((long) bytes[2] & 0xff) << 16
                | ((long) bytes[1] & 0xff) << 8
                | ((long) bytes[0] & 0xff);
    }

    static int byteArrayToInt(byte[] bytes) {
        if (bytes.length<4){
            throw new ArrayIndexOutOfBoundsException ("The length of the provided array is not" +
                    " enough for int number storage: length is " + bytes.length + ", but should be at least 4");
        }
        return ((bytes[0] & 0xFF) << 24) |
                ((bytes[1] & 0xFF) << 16) |
                ((bytes[2] & 0xFF) << 8) |
                (bytes[3] & 0xFF);
    }

    static byte[] intToByteArray(int value) {
        return new byte[]{
                (byte) (value >> 24),
                (byte) (value >> 16),
                (byte) (value >> 8),
                (byte) value};
    }

    static byte[] doubleToByteArray(double amount) {
        if (amount == 0.0){
            return new byte[]{0, 0, 0, 0, 0, 0, 0, 0};
        }
        long longBits = Double.doubleToLongBits(amount);
        return BigInteger.valueOf(longBits).toByteArray();
    }

    static double byteArrayToDouble(byte[] amountInBytes) {
        BigInteger bigIntegerAmount = new BigInteger(amountInBytes);
        long longValue = bigIntegerAmount.longValue();
        return Double.longBitsToDouble(longValue);
    }
}
