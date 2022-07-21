package com.obolonyk.skillup.serialization;

import java.io.*;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Message message = new Message(new Date(), "This is a date", 28.9);
        SerializationMessageDao serializationMessageDao = new SerializationMessageDao();
        serializationMessageDao.save(message);
        Message load = serializationMessageDao.load();
        System.out.println(load);
    }
}
