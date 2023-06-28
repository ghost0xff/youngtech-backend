package com.youngtechcr.www.storage;

import com.youngtechcr.www.domain.OneAmongMany;
import com.youngtechcr.www.exceptions.custom.FileOperationException;
import com.youngtechcr.www.productimage.ProductImageStorageService;
import com.youngtechcr.www.exceptions.ErrorMessages;
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
    // ^^^^ a string representing a posible DIRECTORY, NOT A  PATH to a directory

    public static String generateServerFileName(Integer elementId, MultipartFile fileToBeUploaded, FileType fileType) {
        /*
                    SERVER FILE NAME STRUCTURE
            Formated server file name should look like this:

                {id of element related to file} + "-" + {fileTypeIdentifier} + "-" + {randomGeneratedUuid}

            The first and second "-" (hyphens) indicate the elementId and the type of file that a file stores,
            this is needed since it would be difficult to identify files by only the element id and a random UUID.
            No extensions for files are added because server file name are JUST for storing files in server and
            does't affect clients since original_file_name (wich includes file extension) is stored in database and
            is of established as the file name when downloading from server.
            Examples of how formated file names should look like:
                1-img-7894c665-da24-4f93-a380-43eff9abf1f2
                21-video-7894c665-da24-4f93-a380-43eff9abf1f2
                23-pdf-7894c665-da24-4f93-a380-43eff9abf1f2
                67-xlsOrXlsx-7894c665-da24-4f93-a380-43eff9abf1f2
        */
        UUID randomUuid = UUID.randomUUID();
        String fileTypeIdentifier = fileType.identifier;
        String newServerFileName = elementId.toString() + "-" + fileTypeIdentifier + "-" + randomUuid.toString();
        return newServerFileName;
    }

    public static <T extends FileMetaData & OneAmongMany> void setFileMetaData(
        T file, boolean main, long sizeInBytes, String mimeType
    ){
        file.setMain(main);
        file.setSizeInBytes(sizeInBytes);
        file.setMimeType(mimeType);
    }

    public static Path createDirectoriesIfNotAlreadyExists(Path posibleDirectoryPath) {
        posibleDirectoryPath.toAbsolutePath().normalize();
        if (Files.exists(posibleDirectoryPath) && Files.isRegularFile(posibleDirectoryPath)) {
            log.warn("Can't create directory with name: " + posibleDirectoryPath.getFileName() + " becasuse a regular file already exists in the specified file system path");
            throw new FileOperationException(ErrorMessages.UNABLE_TO_UPLOAD_REQUESTED_FILE);
        }
        try {
            return Files.createDirectories(posibleDirectoryPath);
        } catch (IOException e) {
            log.warn("Can't create directory with name: " + posibleDirectoryPath.getFileName() + " due to...");
            e.printStackTrace(System.out);
            throw new FileOperationException(ErrorMessages.UNABLE_TO_UPLOAD_REQUESTED_FILE);
        }
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
