package com.obolonyk.skillup.serialization;

import com.obolonyk.skillup.io.BufferedInputStream;
import com.obolonyk.skillup.io.BufferedOutputStream;
import com.obolonyk.skillup.io.ByteArrayInputStream;
import com.obolonyk.skillup.io.ByteArrayOutputStream;

import java.io.*;

public class SerializationMessageDao implements MessageDao {
    ByteArrayOutputStream byteArrayOutputStream;

    @Override
    public void save(Message message) throws IOException {
        byteArrayOutputStream = new ByteArrayOutputStream();
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream)) {
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();
        }
    }

    @Override
    public Message load() throws IOException, ClassNotFoundException {
        byte[] bytes = byteArrayOutputStream.toByteArray();
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(byteArrayInputStream);
             ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream)) {
            return (Message) objectInputStream.readObject();
        }
    }
}
