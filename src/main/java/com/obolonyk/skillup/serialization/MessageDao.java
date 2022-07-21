package com.obolonyk.skillup.serialization;

import java.io.IOException;
import java.io.OutputStream;

public interface MessageDao {

    void save (Message message) throws IOException;
    Message load () throws IOException, ClassNotFoundException;
}
