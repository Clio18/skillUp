package com.obolonyk.skillup.echoserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static final int PORT = 3005;
    private static final int BUFFER_CAPACITY = 50;
    private static final String HOST = "localhost";
    private static final String END_PROGRAM = "-1";

    public static void main(String[] args) throws IOException {

        try (Socket socket = new Socket(HOST, PORT);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream())) {

            Scanner scanner = new Scanner(System.in);
            while (true) {
                String line = scanner.nextLine();
                if (line.equals(END_PROGRAM)) {
                    break;
                }
                bufferedOutputStream.write(line.getBytes());
                bufferedOutputStream.flush();

                byte[] buffer = new byte[BUFFER_CAPACITY];
                int read = bufferedInputStream.read(buffer);
                System.out.println(new String(buffer, 0, read));
            }
        }
    }
}
