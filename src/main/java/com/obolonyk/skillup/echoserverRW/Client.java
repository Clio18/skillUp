package com.obolonyk.skillup.echoserverRW;

import java.io.*;
import java.net.Socket;

public class Client {

    private static final int PORT = 3005;
    private static final String HOST = "localhost";
    private static final String END_PROGRAM = "-1";

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(HOST, PORT);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        String message = "New!";
        writer.write(message);
        writer.newLine();
        writer.flush();

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line = reader.readLine();
        System.out.println(line);
    }
}
