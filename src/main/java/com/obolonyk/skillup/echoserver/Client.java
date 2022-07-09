package com.obolonyk.skillup.echoserver;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {

        while (true) {
            try (Socket socket = new Socket("localhost", 3005)) {
                Scanner scanner = new Scanner(System.in);
                String line = scanner.nextLine();
                if (line.equals("-1")) {
                    break;
                }
                socket.getOutputStream().write(line.getBytes());
                byte[] buffer = new byte[50];
                int read = socket.getInputStream().read(buffer);
                System.out.println(new String(buffer, 0, read));
            }
        }
    }
}
