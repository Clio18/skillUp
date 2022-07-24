package com.obolonyk.skillup.serialization;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class SerializationMessageDaoTest {

    @Test
    @DisplayName("Test Load After Save Message")
    void testLoadAfterSaveMessage () throws IOException, ClassNotFoundException {
        Message message = new Message(new Date(), "Message їїїї ййй", 11.1);
        SerializationMessageDao serializationMessageDao = new SerializationMessageDao();
        serializationMessageDao.save(message);
        Message loadedMessage = serializationMessageDao.load();
        assertEquals(message, loadedMessage);
    }

}