package com.obolonyk.skillup.serialization;

import com.obolonyk.skillup.io.BufferedInputStream;
import com.obolonyk.skillup.io.BufferedOutputStream;
import com.obolonyk.skillup.io.ByteArrayInputStream;
import com.obolonyk.skillup.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializationMessageDao implements MessageDao {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);

    @Override
    public void save(Message message) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream);
        objectOutputStream.writeObject(message);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    @Override
    public Message load() throws IOException, ClassNotFoundException {
        byte[] bytes = byteArrayOutputStream.toByteArray();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(byteArrayInputStream);
        ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);
        return (Message) objectInputStream.readObject();
    }
}
