package com.fot.eventsystem.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    private final String uploadDir = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator + "images" + File.separator;

    public String saveFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return "default.png";
        }

        String originalName = file.getOriginalFilename();
        String fileName = System.currentTimeMillis() + "_" + (originalName != null ? originalName.replace(" ", "_") : "image");

        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File saveFile = new File(directory.getAbsolutePath() + File.separator + fileName);
        file.transferTo(saveFile);

        return fileName;
    }

    public void deleteFile(String fileName) {
        if (fileName == null || "default.png".equals(fileName)) {
            return;
        }
        try {
            Path path = Paths.get(uploadDir + fileName);
            Files.deleteIfExists(path);
        } catch (IOException e) {
            System.err.println("Error deleting file: " + e.getMessage());
        }
    }
}
