package org.example.demo;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DirectoryService {

    public List<File> listDirectoryContents(File directory) {
        File[] files = directory.listFiles();
        if (files == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(files);
    }

    public void createDirectory(File directory) {
        if (!directory.exists()) {
            boolean created = directory.mkdir();
            if (!created) {
                System.err.println("Failed to create directory: " + directory.getAbsolutePath());
            }
        }
    }

    public void deleteDirectory(File directory) {
        if (directory.exists()) {
            boolean deleted = directory.delete();
            if (!deleted) {
                System.err.println("Failed to delete directory: " + directory.getAbsolutePath());
            }
        }
    }
}
