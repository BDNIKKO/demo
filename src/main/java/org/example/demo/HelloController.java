package org.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class HelloController {

    @FXML
    private TextField directoryPathField;

    @FXML
    private TextField searchField;

    @FXML
    private TextField sourcePathField;

    @FXML
    private TextField targetPathField;

    @FXML
    private ListView<String> listView;

    private final FileService fileService = new FileService();
    private final DirectoryService directoryService = new DirectoryService();
    private final SearchService searchService = new SearchService();

    @FXML
    public void onDisplayContents() {
        Path directory = Paths.get(directoryPathField.getText());
        List<File> files = directoryService.listDirectoryContents(directory.toFile());
        listView.getItems().clear();
        if (files != null) {
            for (File file : files) {
                listView.getItems().add(file.getName());
            }
        } else {
            System.err.println("Failed to display contents.");
        }
    }

    @FXML
    public void onSearchFiles() {
        Path directory = Paths.get(directoryPathField.getText());
        String query = searchField.getText();
        List<File> results = searchService.searchFiles(directory.toFile(), query);
        listView.getItems().clear();
        for (File file : results) {
            listView.getItems().add(file.getName());
        }
    }

    @FXML
    public void onCreateDirectory() {
        Path directory = Paths.get(directoryPathField.getText());
        boolean success = directoryService.createDirectory(directory.toFile());
        if (success) {
            System.out.println("Directory created successfully.");
            onDisplayContents();
        } else {
            System.err.println("Failed to create directory.");
        }
    }

    @FXML
    public void onDeleteDirectory() {
        Path directory = Paths.get(directoryPathField.getText());
        boolean success = directoryService.deleteDirectory(directory.toFile());
        if (success) {
            System.out.println("Directory deleted successfully.");
            onDisplayContents();
        } else {
            System.err.println("Failed to delete directory.");
        }
    }

    @FXML
    public void onCopyFile() {
        Path source = Paths.get(sourcePathField.getText());
        Path target = Paths.get(targetPathField.getText());
        boolean success = fileService.copyFile(source, target);
        if (success) {
            System.out.println("File copied successfully.");
            onDisplayContents();
        } else {
            System.err.println("Failed to copy file.");
        }
    }

    @FXML
    public void onMoveFile() {
        Path source = Paths.get(sourcePathField.getText());
        Path target = Paths.get(targetPathField.getText());
        boolean success = fileService.moveFile(source, target);
        if (success) {
            System.out.println("File moved successfully.");
            onDisplayContents();
        } else {
            System.err.println("Failed to move file.");
        }
    }

    @FXML
    public void onDeleteFile() {
        Path file = Paths.get(sourcePathField.getText());
        boolean success = fileService.deleteFile(file);
        if (success) {
            System.out.println("File deleted successfully.");
            onDisplayContents();
        } else {
            System.err.println("Failed to delete file.");
        }
    }
}
