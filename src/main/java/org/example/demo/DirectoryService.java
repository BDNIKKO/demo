package org.example.demo;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DirectoryService {

    public boolean deleteDirectory(File directory) {
        if (directory.exists()) {
            deleteRecursively(directory);
            if (!directory.delete()) {
                System.err.println("Failed to delete directory: " + directory.getAbsolutePath());
                return false;
            }
            return true;
        }
        return false;
    }

    private void deleteRecursively(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    deleteRecursively(f);
                }
            }
        }
        if (!file.delete()) {
            System.err.println("Failed to delete file: " + file.getAbsolutePath());
        }
    }

    public boolean createDirectory(File directory) {
        if (!directory.exists()) {
            boolean created = directory.mkdir();
            if (!created) {
                System.err.println("Failed to create directory: " + directory.getAbsolutePath());
                return false;
            }
            return true;
        }
        return false;
    }

    public List<File> listDirectoryContents(File directory) {
        List<File> fileList = new ArrayList<>();
        File[] files = directory.listFiles();
        if (files != null) {
            Collections.addAll(fileList, files);
        } else {
            System.err.println("Failed to list contents of directory: " + directory.getAbsolutePath());
        }
        return fileList;
    }
}
