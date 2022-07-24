package com.obolonyk.skillup.echoserverRW;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 3005;

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT);
             Socket socket = serverSocket.accept();
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            while (true) {
                String line = reader.readLine();
                String resp = "Echo: " + line;
                writer.write(resp);
                writer.newLine();
                writer.flush();
            }
        }
    }
}
