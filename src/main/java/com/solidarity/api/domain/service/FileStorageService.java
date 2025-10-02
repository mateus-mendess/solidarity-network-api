package com.solidarity.api.domain.service;

import com.solidarity.api.config.FileStorageConfig;
import com.solidarity.api.exception.FileStorageException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
}
