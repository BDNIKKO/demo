package org.example.demo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SearchService {

    public List<File> searchFiles(File directory, String query) {
        List<File> resultList = new ArrayList<>();
        searchFilesRecursive(directory, query, resultList);
        return resultList;
    }

    private void searchFilesRecursive(File directory, String query, List<File> resultList) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    searchFilesRecursive(file, query, resultList);
                } else if (file.getName().contains(query)) {
                    resultList.add(file);
                }
            }
        }
    }
}
