package org.example.demo;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DirectoryService {

    public boolean deleteDirectory(File directory) {
        if (directory.exists()) {
            System.out.println("Attempting to delete directory: " + directory.getAbsolutePath());

            // First, delete the contents recursively
            deleteRecursively(directory);

            // Attempt to delete the directory itself
            boolean deleted = directory.delete();
            if (deleted) {
                System.out.println("Directory deleted successfully: " + directory.getAbsolutePath());
                return true;
            } else {
                System.err.println("Failed to delete directory: " + directory.getAbsolutePath());

                // Double-check if directory still exists before concluding failure
                if (directory.exists()) {
                    System.err.println("Directory still exists after deletion attempt: " + directory.getAbsolutePath());
                }
                return false;
            }
        } else {
            System.err.println("Directory does not exist, nothing to delete: " + directory.getAbsolutePath());
            return false;
        }
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
