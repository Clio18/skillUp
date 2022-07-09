package com.obolonyk.skillup.echoserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(3005)) {
            while (true) {
                Socket socket = serverSocket.accept();
                byte[] buffer = new byte[50];
                int read = socket.getInputStream().read(buffer);
                String message = new String(buffer, 0, read);
                String response = "Echo: " + message;
                socket.getOutputStream().write(response.getBytes());
            }
        }
    }
}
