package org.example.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileService {

    public void copyFile(String sourcePath, String targetPath) throws IOException {
        Path source = Paths.get(sourcePath);
        Path target = Paths.get(targetPath);
        Files.copy(source, target);
    }

    public void moveFile(String sourcePath, String targetPath) throws IOException {
        Path source = Paths.get(sourcePath);
        Path target = Paths.get(targetPath);
        Files.move(source, target);
    }

    public void deleteFile(String filePath) throws IOException {
        Path file = Paths.get(filePath);
        Files.delete(file);
    }
}
