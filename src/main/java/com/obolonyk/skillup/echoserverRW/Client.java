package com.obolonyk.skillup.echoserverRW;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static final int PORT = 3005;
    private static final String HOST = "localhost";
    private static final String END_PROGRAM = "-1";

    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket(HOST, PORT);
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {
            while (true) {
                String message = scanner.nextLine();
                if (message.equals(END_PROGRAM)) {
                    return;
                }
                writer.write(message);
                writer.newLine();
                writer.flush();
                String line = reader.readLine();
                System.out.println(line);
            }
        }
    }
}
