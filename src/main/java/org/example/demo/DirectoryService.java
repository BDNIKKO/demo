package org.example.demo;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DirectoryService {

    public boolean deleteDirectory(File directory) {
        if (directory.exists()) {
            System.out.println("Attempting to delete directory: " + directory.getAbsolutePath());

            // Delete contents recursively
            boolean deletedContents = deleteRecursively(directory);

            // If contents were deleted successfully, try to delete the directory itself
            if (deletedContents && directory.delete()) {
                System.out.println("Directory deleted successfully: " + directory.getAbsolutePath());
                return true;
            } else if (!directory.exists()) {
                System.out.println("Directory already deleted: " + directory.getAbsolutePath());
                return true;
            } else {
                System.err.println("Failed to delete directory: " + directory.getAbsolutePath());
                return false;
            }
        } else {
            System.err.println("Directory does not exist, nothing to delete: " + directory.getAbsolutePath());
            return false;
        }
    }

    private boolean deleteRecursively(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (!deleteRecursively(f)) {
                        return false;
                    }
                }
            }
        }

        if (file.delete()) {
            System.out.println("Deleted: " + file.getAbsolutePath());
            return true;
        } else if (!file.exists()) {
            System.out.println("File or directory already deleted: " + file.getAbsolutePath());
            return true;  // Consider it a success if it's already gone
        } else {
            System.err.println("Failed to delete file: " + file.getAbsolutePath());
            return false;
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
