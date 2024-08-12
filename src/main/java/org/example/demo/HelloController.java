package org.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Node;

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
    public void onBrowseDirectory(javafx.event.ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            directoryPathField.setText(selectedDirectory.getAbsolutePath());
        }
    }

    @FXML
    public void onBrowseSourceFile(javafx.event.ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            sourcePathField.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    public void onBrowseTargetFile(javafx.event.ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showSaveDialog(stage);
        if (selectedFile != null) {
            targetPathField.setText(selectedFile.getAbsolutePath());
        }
    }

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
        System.out.println("onDeleteDirectory method called");
        Path directory = Paths.get(directoryPathField.getText());
        File directoryFile = directory.toFile();

        if (!directoryFile.exists()) {
            System.err.println("Directory does not exist: " + directoryFile.getAbsolutePath());
            return;
        }

        if (!directoryFile.isDirectory()) {
            System.err.println("The path is not a directory: " + directoryFile.getAbsolutePath());
            return;
        }

        if (!directoryFile.canWrite()) {
            System.err.println("No write permission for directory: " + directoryFile.getAbsolutePath());
            return;
        }

        if (!directoryFile.canExecute()) {
            System.err.println("No execute permission for directory: " + directoryFile.getAbsolutePath());
            return;
        }

        boolean success = directoryService.deleteDirectory(directoryFile);
        if (success) {
            System.out.println("Directory deleted successfully: " + directoryFile.getAbsolutePath());
            onDisplayContents(); // Refresh the display
        } else {
            System.err.println("Failed to delete directory: " + directoryFile.getAbsolutePath());
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
