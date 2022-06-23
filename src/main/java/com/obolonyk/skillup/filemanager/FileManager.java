package com.obolonyk.skillup.filemanager;

import java.io.*;

public class FileManager {

    private FileManager() {
        throw new IllegalStateException("Utility class");
    }

    // public static int countFiles(String path) - принимает путь к папке,
    // возвращает количество файлов в папке и всех подпапках по пути
    public static int countFiles(String path) {
        File[] listFiles = getListOfFiles(path);
        int counter = 0;
        for (File listFile : listFiles) {
            if (!listFile.isDirectory()) {
                counter++;
            } else {
                counter = counter + countFiles(listFile.getPath());
            }
        }
        return counter;
    }

    // public static int countDirs(String path) - принимает путь к папке,
    // возвращает количество папок в папке и всех подпапках по пути
    public static int countDirs(String path) {
        File[] listFiles = getListOfFiles(path);
        int counter = 0;
        for (File listFile : listFiles) {
            if (listFile.isDirectory()) {
                counter++;
                counter = counter + countDirs(listFile.getPath());
            }
        }
        return counter;
    }

    // - метод по копированию папок и файлов.
    //Параметр from - путь к файлу или папке,
    // параметр to - путь к папке куда будет производиться копирование.
    public static void copy(String from, String to) throws IOException {
        File[] files = getListOfFiles(from);
        for (File element : files) {
            String newPath = to + File.separator + element.getName();
            if (element.isFile()) {
                byte[] bytes;
                try (InputStream inputStream = new FileInputStream(element.getAbsoluteFile());
                     OutputStream outputStream = new FileOutputStream(newPath)) {
                    bytes = inputStream.readAllBytes();
                    outputStream.write(bytes);
                }
            } else {
                File file = new File(newPath);
                if (!file.mkdir()) {
                    throw new NullPointerException("The folder was not created on path: " + newPath);
                }
                copy(from + File.separator + element.getName(), to + File.separator + element.getName());
            }
        }
    }

    //- метод по перемещению папок и файлов.
    //Параметр from - путь к файлу или папке, параметр to -
    // путь к папке куда будет производиться копирование
    public static boolean move(String from, String to) {
        return new File(from).renameTo(new File(to));
    }

    static File[] getListOfFiles(String path) {
        if (path == null) {
            throw new IllegalArgumentException("Please provide a path");
        }
        File file = new File(path);
        File[] listFiles = file.listFiles();
        if (listFiles == null) {
            throw new NullPointerException("The files do not exist on provided path " + path);
        }
        return listFiles;
    }
}
