package com.youngtechcr.www.services.storage;

import com.youngtechcr.www.domain.Product;
import com.youngtechcr.www.domain.storage.FileData;
import com.youngtechcr.www.domain.storage.ProductAndProductImageRelationship;
import com.youngtechcr.www.domain.storage.ProductImageFileData;
import com.youngtechcr.www.exceptions.custom.FileOperationException;
import com.youngtechcr.www.exceptions.custom.NoDataForRequestedObjectFoundException;
import com.youngtechcr.www.services.domain.ProductService;
import com.youngtechcr.www.utils.LocalFileSystemPaths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import static com.youngtechcr.www.utils.LocalFileSystemPaths.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.UUID;
import java.util.stream.Collector;

@Service
public class ProductImageFileDataStorageService implements StorageService {

    @Autowired
    private ProductService productService;

    @Transactional
    public void processUpload(Integer productId, MultipartFile imageToBeUploaded) {
        String originalFileName = imageToBeUploaded.getOriginalFilename();
        String serverFileName = null;
        if (this.productService.existsById(productId)) {
            Product requestedProduct = productService.findById(productId);
            serverFileName = this.generateServerFileName(Integer.toString(productId));
            Path newProducImagePath = getProductPath(productId.toString()).resolve(serverFileName);
            saveToFileSystem(newProducImagePath, imageToBeUploaded);
        } else  {
            throw new NoDataForRequestedObjectFoundException("No product with id: " + productId + " was found");
        }
    }

    @Override
    public void saveToFileSystem(Path pathToBeSaved, MultipartFile imageToBeSaved) {
        try {
            Files.copy(imageToBeSaved.getInputStream(), pathToBeSaved);
        } catch (IOException e) {
            throw new FileOperationException("Can't upload file, a file operation exception occured in server");
        }
    }

    @Override
    public String generateServerFileName(String productIdString) {
        UUID newRandomUuid = UUID.randomUUID();
        String newServerFileName = productIdString + "-" + newRandomUuid;
        return newServerFileName;
    }

    private Path getProductPath(String productId) {
        String serverFileName = this.generateServerFileName(productId);
        Path productDirectoryPath = Path
                .of(PRODUCTS_STORAGE_DIRECTORY)
                .resolve(productId)
                .normalize()
                .toAbsolutePath();
        if(Files.exists(productDirectoryPath)){
            return productDirectoryPath;
        } else {
            try {
                Files.createDirectory(productDirectoryPath);

            } catch (IOException e) {
                throw new FileOperationException("Can't process file, a file operation exception occured in server");
            }
        }
    }

    @Override
    public FileData saveRecordToDataBase(FileData fileData) {
        return null;
    }

    @Override
    public FileData retrieveFromFileSystem(FileData fileData) {
        return null;
    }

    public Path existsInFileSystem(String imageServerName) {
        return null;
    }

}
