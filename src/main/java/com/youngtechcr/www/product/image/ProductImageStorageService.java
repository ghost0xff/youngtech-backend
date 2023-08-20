package com.youngtechcr.www.product.image;

import com.youngtechcr.www.domain.TimestampedUtils;
import com.youngtechcr.www.product.Product;
import com.youngtechcr.www.exceptions.custom.AlreadyExistsException;
import com.youngtechcr.www.exceptions.custom.FileOperationException;
import com.youngtechcr.www.exceptions.custom.NoDataFoundException;
import com.youngtechcr.www.product.ProductService;
import com.youngtechcr.www.storage.StorageService;
import com.youngtechcr.www.storage.FileType;
import com.youngtechcr.www.exceptions.HttpErrorMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.youngtechcr.www.storage.StorageUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ProductImageStorageService implements StorageService<ProductImage, Product> {

    private static final Logger log = LoggerFactory.getLogger(ProductImageStorageService.class);
    private final ProductImageRepository productImageRepo;
    private final ProductService productService;

    public ProductImageStorageService(
            ProductImageRepository productImageRepo,
            ProductService productService) {
        this.productImageRepo = productImageRepo;
        this.productService = productService;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ProductImage store(Product relatedProduct, MultipartFile image) {
        Integer productId = relatedProduct.getProductId();
        Path relatedProductDir = Paths
                .get(StorageUtils.PRODUCT_DIRECTORY).resolve(productId.toString())
                .resolve(StorageUtils.IMAGE_DIRECTORY).toAbsolutePath().normalize();
        StorageUtils.createDirectoriesIfNotAlreadyExists(relatedProductDir);
        log.debug("Created directory(ies?) at " + relatedProductDir.toString());
        String serverName = StorageUtils.generateServerName(productId, image, FileType.IMAGE);
        Path imagePath = relatedProductDir.resolve(serverName);
        ProductImage toBeStored = ProductImage.builder()
                .withServerName(serverName)
                .withOriginalName(image.getOriginalFilename())
                .withRelativePath(imagePath.toString())
                .withMimeType(image.getContentType())
                .withSizeInBytes(image.getSize())
                .withProduct(relatedProduct)
                .build();
        TimestampedUtils.setTimestampsToNow(toBeStored);
        StorageUtils.saveFileOrReplaceIfExisting(image, imagePath);
        ProductImage storedProductImage = productImageRepo.save(toBeStored);
        log.debug("Saved product image to filesystem and database, with metadata -> " + toBeStored);
        return storedProductImage;
    }

    @Override
    public Resource obtain(ProductImage productImage) {
        Path absoluteImagePath = Path.of(productImage.getRelativePath()).toAbsolutePath().normalize();
        Resource imageResource = null;
        if (Files.exists(absoluteImagePath) && !Files.isDirectory(absoluteImagePath)) {
            try {
                imageResource = new UrlResource(absoluteImagePath.toUri());
                return imageResource;
            } catch (MalformedURLException malformedURLException) {
                log.warn("Couldn't download file due to malformed url/uri exception created from file path");
                malformedURLException.printStackTrace();
                throw new FileOperationException(HttpErrorMessages.UNABLE_TO_DOWNLOAD_REQUESTED_FILE);
            }
        }
        throw new NoDataFoundException(HttpErrorMessages.UNABLE_TO_LOCATE_REQUESTED_FILE);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void dump(ProductImage productImage) {
        Path imagePath = Path.of(productImage.getRelativePath());
        try {
            Files.delete(imagePath);
        } catch (IOException e) {
            log.warn("IOException interrupted file deletion");
            e.printStackTrace();
            throw new FileOperationException(HttpErrorMessages.UNABLE_TO_DELETE_REQUESTED_FILE);
        }
        this.productImageRepo.deleteById(productImage.getProductImageId());
    }

}
