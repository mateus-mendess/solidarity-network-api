package com.solidarity.api.model.service;

import com.solidarity.api.config.FileStorageConfig;
import com.solidarity.api.exception.FileStorageException;
import com.solidarity.api.exception.NotFoundException;
import com.solidarity.api.exception.SolidarityException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageService(FileStorageConfig fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
    }

    public String uploadFile(MultipartFile file, String subFolder) {
        String fileName = UUID.randomUUID() + "-" + StringUtils.cleanPath(file.getOriginalFilename());

        try {
            Path folder = fileStorageLocation.resolve(subFolder);
            Path targetLocation = folder.resolve(fileName);
            file.transferTo(targetLocation);
            return subFolder + "/" +fileName;
        } catch (IOException exception) {
            throw new FileStorageException("Error saving file");
        }
    }

    public Resource loadFileAsResource(String relativePath) {
        try {
            String decodePath = URLDecoder.decode(relativePath, StandardCharsets.UTF_8);
            Path filePath = fileStorageLocation.resolve(decodePath).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new NotFoundException("File not found or not readable");
            }
        } catch (MalformedURLException exception) {
            throw new SolidarityException("Error: " + exception);
        }
    }

    public void deleteFile(String relativePath) {
        try {
            if (relativePath != null) {
                String decodedPath = URLDecoder.decode(relativePath, StandardCharsets.UTF_8);
                Path filePath = fileStorageLocation.resolve(decodedPath).normalize();

                File file = filePath.toFile();
                if (file.exists()) {
                    boolean deleted = file.delete();
                    if (!deleted) {
                        throw new FileStorageException("Failed to delete file");
                    }
                }
            }
        } catch (Exception exception) {
            throw new FileStorageException("Error deleting file: " + exception.getMessage());
        }
    }
}
