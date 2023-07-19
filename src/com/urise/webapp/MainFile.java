package com.urise.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
public class MainFile {

    public static void printPathWithSpaces(Path path, int spacesCount){
        System.out.println(String.format("%" + spacesCount + "s", " ") + path);
    }

    public static void printDirectory(Path path, int spacesCount) {
        if (spacesCount == 0) {
            System.out.println(path);
        } else {
            printPathWithSpaces(path, spacesCount);
        }
        try {
            Files.list(path).forEach(e -> {
                if (Files.isDirectory(e)) {
                    printDirectory(e, spacesCount + 4);
                } else {
                    printPathWithSpaces(e, spacesCount + 4);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String filePath = "./.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("./src/com/urise/webapp");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println();
        System.out.println("Recursive traversal of the files in directories and subdirectories");
        Path path = Path.of("./src/com/urise/webapp");
        printDirectory(path, 0);
    }
}