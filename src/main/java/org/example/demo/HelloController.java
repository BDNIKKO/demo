package org.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class HelloController {

    @FXML
    private ListView<String> fileList;

    @FXML
    private TextField directoryPathField;

    @FXML
    private TextField searchField;

    private final DirectoryService directoryService = new DirectoryService();
    private final FileService fileService = new FileService();
    private final SearchService searchService = new SearchService();
    private final LoggingService loggingService = new LoggingService();

    @FXML
    public void onDisplayContents() {
        try {
            String directoryPath = directoryPathField.getText();
            File directory = new File(directoryPath);
            fileList.getItems().clear();

            List<File> files = directoryService.listDirectoryContents(directory);
            for (File file : files) {
                fileList.getItems().add(file.getName() + " - " + file.length() + " bytes - " + file.lastModified());
            }
        } catch (Exception e) {
            loggingService.logException(e);
        }
    }

    @FXML
    public void onCreateDirectory() {
        try {
            String directoryPath = directoryPathField.getText();
            File directory = new File(directoryPath);
            directoryService.createDirectory(directory);
            onDisplayContents();
        } catch (Exception e) {
            loggingService.logException(e);
        }
    }

    @FXML
    public void onDeleteDirectory() {
        try {
            String directoryPath = directoryPathField.getText();
            File directory = new File(directoryPath);
            directoryService.deleteDirectory(directory);
            onDisplayContents();
        } catch (Exception e) {
            loggingService.logException(e);
        }
    }

    @FXML
    public void onSearchFiles() {
        try {
            String directoryPath = directoryPathField.getText();
            String query = searchField.getText();
            File directory = new File(directoryPath);
            fileList.getItems().clear();

            List<File> results = searchService.searchFiles(directory, query);
            for (File file : results) {
                fileList.getItems().add(file.getName());
            }
        } catch (Exception e) {
            loggingService.logException(e);
        }
    }

    @FXML
    public void onCopyFile() {
        try {
            String sourcePath = directoryPathField.getText() + "/sourceFile.txt";
            String targetPath = directoryPathField.getText() + "/targetFile.txt";
            fileService.copyFile(sourcePath, targetPath);
            onDisplayContents();
        } catch (IOException e) {
            loggingService.logException(e);
        }
    }

    @FXML
    public void onMoveFile() {
        try {
            String sourcePath = directoryPathField.getText() + "/sourceFile.txt";
            String targetPath = directoryPathField.getText() + "/targetFile.txt";
            fileService.moveFile(sourcePath, targetPath);
            onDisplayContents();
        } catch (IOException e) {
            loggingService.logException(e);
        }
    }

    @FXML
    public void onDeleteFile() {
        try {
            String filePath = directoryPathField.getText() + "/fileToDelete.txt";
            fileService.deleteFile(filePath);
            onDisplayContents();
        } catch (IOException e) {
            loggingService.logException(e);
        }
    }
}
