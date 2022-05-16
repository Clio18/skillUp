package com.obolonyk.skillUp.reflection.util;

public class Util {

    public static String showTheLicense(String license) {
        return "The driving licence " + license;
    }

    public final String showTheCar(String license) {
        return "This is my car";
    }

    public final int showHowManyCar(int cars, String name) {
        System.out.println(name);
        return cars;
    }

    public static String keepMyLicense() {
        return "I keep driving licence all the time";
    }

    public static double getTheDistance() {
        return 1999.99;
    }

    private static String getTestNameForDisplayName(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        char[] chars = string.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if ((byte) chars[0] >= 97) {
                chars[0] = (char) ((byte) chars[0] - 32);
            }
            if ((byte) chars[i] >= 65 && (byte) chars[i] <= 90 && i != 0) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(chars[i]);
        }
        return stringBuilder.toString();
    }

    String showAddress(String address) {
        return "This is my address " + address;
    }

    String showEmail() {
        return "This is my email!";
    }
}
