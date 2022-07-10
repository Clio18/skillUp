package com.obolonyk.skillup.echoserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 3005;
    private static final int BUFFER_CAPACITY = 50;

    public static void main (String [] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        try (Socket socket = serverSocket.accept();
             BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream())) {
            while (true) {
                byte[] buffer = new byte[BUFFER_CAPACITY];
                int read = bufferedInputStream.read(buffer);

                String message = new String(buffer, 0, read);
                String response = "Echo: " + message;
                bufferedOutputStream.write(response.getBytes());
                bufferedOutputStream.flush();
            }
        }
    }
}
