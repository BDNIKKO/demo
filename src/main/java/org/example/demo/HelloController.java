package org.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class HelloController {

    public HBox actionButtonsBox;
    @FXML
    private TextField directoryPathField;

    @FXML
    private TextField searchField;

    @FXML
    private TextField targetPathField;

    @FXML
    private ListView<String> listView;

    private final FileService fileService = new FileService();
    private final DirectoryService directoryService = new DirectoryService();
    private final SearchService searchService = new SearchService();

    private Path currentDirectory;

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
    public void onBrowseTargetFile(javafx.event.ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            targetPathField.setText(selectedDirectory.getAbsolutePath());
        }
    }

    @FXML
    public void onDisplayContents() {
        currentDirectory = Paths.get(directoryPathField.getText());
        List<File> files = directoryService.listDirectoryContents(currentDirectory.toFile());
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
        currentDirectory = Paths.get(directoryPathField.getText());
        String query = searchField.getText();
        List<File> results = searchService.searchFiles(currentDirectory.toFile(), query);
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
        handleFileOperation(FileOperation.COPY);
    }

    @FXML
    public void onMoveFile() {
        handleFileOperation(FileOperation.MOVE);
    }

    @FXML
    public void onDeleteFile() {
        handleFileOperation(FileOperation.DELETE);
    }

    private void handleFileOperation(FileOperation operation) {
        String selectedFileName = listView.getSelectionModel().getSelectedItem();
        if (selectedFileName == null || (operation != FileOperation.DELETE && targetPathField.getText().isEmpty())) {
            System.err.println("No file selected or target path is empty.");
            return;
        }

        Path source = currentDirectory.resolve(selectedFileName);
        Path target = operation != FileOperation.DELETE ? Paths.get(targetPathField.getText()).resolve(selectedFileName) : null;

        boolean success = false;
        switch (operation) {
            case COPY:
                success = fileService.copyFile(source, target);
                break;
            case MOVE:
                success = fileService.moveFile(source, target);
                break;
            case DELETE:
                success = fileService.deleteFile(source);
                break;
        }

        if (success) {
            System.out.println("File " + operation.name().toLowerCase() + "d successfully.");
            onDisplayContents();
        } else {
            System.err.println("Failed to " + operation.name().toLowerCase() + " file.");
        }
    }

    private enum FileOperation {
        COPY, MOVE, DELETE
    }
}
