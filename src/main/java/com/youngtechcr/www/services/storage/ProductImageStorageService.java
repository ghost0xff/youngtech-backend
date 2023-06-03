package com.youngtechcr.www.services.storage;

import com.youngtechcr.www.domain.Product;
import com.youngtechcr.www.domain.storage.ProductImageFileData;
import com.youngtechcr.www.exceptions.custom.AlreadyExistsException;
import com.youngtechcr.www.repositories.ProductImageFileDataRepository;
import com.youngtechcr.www.services.ProductService;
import com.youngtechcr.www.utils.ErrorMessages;
import com.youngtechcr.www.utils.TimestampUtils;
import jakarta.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.youngtechcr.www.utils.StorageUtils;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ProductImageStorageService implements FileSystemStorageService<ProductImageFileData> {

    private static final Logger log = LoggerFactory.getLogger(ProductImageStorageService.class);

    @Autowired
    private ProductImageFileDataRepository productImageFileDataRepository;
    @Lazy
    @Autowired
    private ProductService productService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ProductImageFileData storeProductImage(
            Integer productId, MultipartFile imageToBeUploaded,
            @Nullable ProductImageFileData imageMetaData
    ) {
        Product productWhoseImageWillBeAdded = this.productService.findProductById(productId);
        if(!alreadyHasMainImage(productWhoseImageWillBeAdded)){
            Path posibleProductDirectory = Paths
                    .get(StorageUtils.PRODUCT_STORAGE_DIRECTORY)
                    .resolve(productId.toString())
                    .resolve(StorageUtils.IMAGE_DIRECTORY);
            String serverFileName = StorageUtils.generateServerFileName(productId, imageToBeUploaded);
            var savedProductImageInWithSomeMetaData = this.saveToFileSystem(serverFileName, posibleProductDirectory, imageToBeUploaded);
            savedProductImageInWithSomeMetaData.setMain(imageMetaData != null ? imageMetaData.isMain() : false);
            savedProductImageInWithSomeMetaData.setSizeInBytes(imageToBeUploaded.getSize());
            savedProductImageInWithSomeMetaData.setMimeType(imageToBeUploaded.getContentType());
            savedProductImageInWithSomeMetaData.setProduct(productWhoseImageWillBeAdded);
            ProductImageFileData savedProductImageRepresentation = this.saveToDataBase(savedProductImageInWithSomeMetaData);
            log.info("Stored (in file system and in database) product image in server successfully: " + savedProductImageRepresentation);
            return savedProductImageRepresentation;
        }
        throw new AlreadyExistsException(ErrorMessages.CANT_CREATE_DUPLICATE_MAIN_IMAGE);
    }

    @Override
    public ProductImageFileData saveToFileSystem(String serverFileName, Path posibleProductDirectoryPath, MultipartFile imageToBeUploaded) {
        StorageUtils.createDirectoriesIfNotAlreadyExists(posibleProductDirectoryPath);
        Path relativeImagePath = posibleProductDirectoryPath.resolve(serverFileName);
        StorageUtils.saveFileOrReplaceIfExisting(imageToBeUploaded, relativeImagePath);
        var savedProductImageInFileSystem = new ProductImageFileData(
            serverFileName,
            imageToBeUploaded.getOriginalFilename(),
            relativeImagePath.toString()
        );
        return savedProductImageInFileSystem;
    }

    @Override
    @Transactional
    public ProductImageFileData saveToDataBase(ProductImageFileData productImageToBeSaved) {
        TimestampUtils.setTimestampsToNow(productImageToBeSaved);
        var createdProductImageRepresentation = this.productImageFileDataRepository.save(productImageToBeSaved);
        return createdProductImageRepresentation;
    }

    @Transactional
    public boolean alreadyHasMainImage(Product productToBeInspected) {
        boolean alreadyHasMainImage = productToBeInspected
                .getImageList()
                .parallelStream()
                .anyMatch((image) -> image.isMain());
        return alreadyHasMainImage ? alreadyHasMainImage : false;
    }
}
