package com.youngtechcr.www.storage;

import com.youngtechcr.www.domain.OneAmongMany;
import com.youngtechcr.www.exceptions.custom.FileOperationException;
import com.youngtechcr.www.product.image.ProductImageStorageService;
import com.youngtechcr.www.exceptions.HttpErrorMessages;
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
    public static final String PRODUCT_DIRECTORY = "../youngtech-storage/products";
    public static final String USER_DIRECTORY = "../youngtech-storage/users";
    public static final String IMAGE_DIRECTORY = "images";

    public static String generateServerName(Integer elementId,MultipartFile file, FileType type) {
        String randomString = UUID.randomUUID().toString().replace("-","");
        String typeIdentifier = type.identifier;
        String serverName = elementId.toString() + "-" + typeIdentifier + "-" + randomString;
        return serverName; // should be something line  1-img-32131312313debc231
    }

    public static <T extends Storable & OneAmongMany> void setFileMetadata(
//      T file, String serverName, String originalName, String relativePath, isMain, String mimetype,
        T file, boolean main, long sizeInBytes, String mimeType
    ){
//        file.setMain(main);
//        file.setSizeInBytes(sizeInBytes);
//        file.setMimeType(mimeType);
    }

    public static Path createDirectoriesIfNotAlreadyExists(Path posibleDirectoryPath) {
        posibleDirectoryPath.toAbsolutePath().normalize();
        if (Files.exists(posibleDirectoryPath) && Files.isRegularFile(posibleDirectoryPath)) {
            log.warn("Can't create directory with name: " + posibleDirectoryPath.getFileName() + " becasuse a regular file already exists in the specified file system path");
            throw new FileOperationException(HttpErrorMessages.UNABLE_TO_UPLOAD_REQUESTED_FILE);
        }
        try {
            return Files.createDirectories(posibleDirectoryPath);
        } catch (IOException e) {
            log.warn("Can't create directory with name: " + posibleDirectoryPath.getFileName() + " due to...");
            e.printStackTrace(System.out);
            throw new FileOperationException(HttpErrorMessages.UNABLE_TO_UPLOAD_REQUESTED_FILE);
        }
    }


    public static void saveFileOrReplaceIfExisting(MultipartFile file, Path path) {
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.warn("Can't create file with name: " + path.getFileName() + " due to...");
            e.printStackTrace(System.out);
            throw new FileOperationException(HttpErrorMessages.UNABLE_TO_UPLOAD_REQUESTED_FILE);
        }
    }

}
