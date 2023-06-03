package com.youngtechcr.www.utils;

import com.youngtechcr.www.exceptions.custom.FileOperationException;
import com.youngtechcr.www.services.storage.ProductImageStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public final class StorageUtils {

    private static final Logger log = LoggerFactory.getLogger(ProductImageStorageService.class);

    public static final String ROOT_STORAGE_DIRECTORY = "../youngtech-storage";
    public static final String PRODUCT_STORAGE_DIRECTORY = "../youngtech-storage/products";
    public static final String USERS_STORAGE_DIRECTORY = "../youngtech-storage/users";
    public static final String IMAGE_DIRECTORY = "images";
    // ^^^^ a string representing a DIRECTORY, NOT A  PATH to a directory

    public static String generateServerFileName(Integer elementId, MultipartFile fileToBeUploaded) {
        UUID randomUuid = UUID.randomUUID();
        String newServerFileName = elementId.toString() + "-" + randomUuid.toString();
        return newServerFileName;
    }

    public static Path createDirectoriesIfNotAlreadyExists(Path posibleDirectoryPath) {
        posibleDirectoryPath.toAbsolutePath().normalize();
        if (Files.notExists(posibleDirectoryPath)) {
            try {
                return Files.createDirectories(posibleDirectoryPath);
            } catch (IOException e) {
                log.warn("Can't create directory with name: " + posibleDirectoryPath.getFileName() + " due to...");
                e.printStackTrace(System.out);
                throw new FileOperationException(ErrorMessages.UNABLE_TO_UPLOAD_REQUESTED_FILE);
            }
        } else if( Files.isRegularFile(posibleDirectoryPath)) {
            log.warn("Can't create directory with name: " + posibleDirectoryPath.getFileName() + " becasuse a regular file already exists in the specified file system path");
            throw new FileOperationException(ErrorMessages.UNABLE_TO_UPLOAD_REQUESTED_FILE);
        }
        return posibleDirectoryPath;
    }

    public static void saveFileOrReplaceIfExisting(MultipartFile imageToBeUploaded, Path relativeImagePath) {
        try {
            Files.copy(imageToBeUploaded.getInputStream(), relativeImagePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.warn("Can't create file with name: " + relativeImagePath.getFileName() + " due to...");
            e.printStackTrace(System.out);
            throw new FileOperationException(ErrorMessages.UNABLE_TO_UPLOAD_REQUESTED_FILE);
        }
    }

}
