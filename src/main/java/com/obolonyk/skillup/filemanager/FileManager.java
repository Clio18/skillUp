package com.obolonyk.skillup.filemanager;

import java.io.File;

public class FileManager {

    // public static int countFiles(String path) - принимает путь к папке,
    // возвращает количество файлов в папке и всех подпапках по пути
    public static int countFiles(String path) {
        File file = new File(path);
        File[] listFiles = file.listFiles();
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
        File file = new File(path);
        File[] listFiles = file.listFiles();
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

    //- метод по перемещению папок и файлов.
    //Параметр from - путь к файлу или папке, параметр to -
    // путь к папке куда будет производиться копирование


}
