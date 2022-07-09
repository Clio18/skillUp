package com.obolonyk.skillup.echoserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(3005);
        try (Socket socket = serverSocket.accept();
             BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());) {
            while (true) {
                byte[] buffer = new byte[50];
                int read = bufferedInputStream.read(buffer);

                String message = new String(buffer, 0, read);
                String response = "Echo: " + message;
                bufferedOutputStream.write(response.getBytes());
                bufferedOutputStream.flush();
            }
        }
    }
}
