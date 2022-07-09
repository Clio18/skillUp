package com.obolonyk.skillup.echoserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {

        try (Socket socket = new Socket("localhost", 3005);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream())) {

            Scanner scanner = new Scanner(System.in);
            while (true) {
                String line = scanner.nextLine();
                if (line.equals("-1")) {
                    break;
                }
                bufferedOutputStream.write(line.getBytes());
                bufferedOutputStream.flush();

                byte[] buffer = new byte[50];
                int read = bufferedInputStream.read(buffer);
                System.out.println(new String(buffer, 0, read));
            }
        }
    }
}
