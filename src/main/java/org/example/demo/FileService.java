package org.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileService {

    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    public boolean copyFile(Path source, Path target) {
        try {
            Files.copy(source, target);
            return true;
        } catch (IOException e) {
            logger.error("Failed to copy file: {} to {}", source, target, e);
            return false;
        }
    }

    public boolean moveFile(Path source, Path target) {
        try {
            Files.move(source, target);
            return true;
        } catch (IOException e) {
            logger.error("Failed to move file: {} to {}", source, target, e);
            return false;
        }
    }

    public boolean deleteFile(Path file) {
        try {
            Files.delete(file);
            return true;
        } catch (IOException e) {
            logger.error("Failed to delete file: {}", file, e);
            return false;
        }
    }
}
