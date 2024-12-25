package com.nandana.transactapi.service.impl;

import com.nandana.transactapi.exception.CustomException;
import com.nandana.transactapi.service.IFileStorageService;
import com.nandana.transactapi.strings.ExceptionStrings;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class FileStorageServiceImpl implements IFileStorageService {
    @Value("${base.url.static.folder}")
    private String PUBLIC_FOLDER_URL;

    @Value("${static.folder.path}")
    private String STATIC_FOLDER_PATH;

    @Override
    public String storeFile(MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".")) : "";

            if (!Arrays.asList(".jpeg", ".png").contains(extension)) {
                throw new CustomException(HttpStatus.BAD_REQUEST.value(), ExceptionStrings.INVALID_IMAGE_FORMAT);
            }

            String fileName = UUID.randomUUID() + extension;

            Path staticFolderPath = Paths.get(STATIC_FOLDER_PATH);
            Path targetLocation = staticFolderPath.resolve(fileName);

            Files.createDirectories(targetLocation.getParent());
            Files.copy(file.getInputStream(), targetLocation);

            return String.format("%s/%s", PUBLIC_FOLDER_URL, fileName);
        } catch (IOException e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionStrings.CANNOT_SAVE_FILE);
        }
    }

    @Override
    public void deleteFile(String filePath) {
        try {
            String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);

            Path staticFolderPath = Paths.get(STATIC_FOLDER_PATH);

            Path targetLocation = staticFolderPath.resolve(fileName);

            Files.deleteIfExists(targetLocation);
        } catch (IOException e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR.value(), ExceptionStrings.CANNOT_DELETE_FILE);
        }
    }
}
