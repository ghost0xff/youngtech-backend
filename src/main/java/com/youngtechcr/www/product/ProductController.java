package com.youngtechcr.www.product;

import com.youngtechcr.www.product.image.ProductImageService;
import com.youngtechcr.www.storage.DualNameFileCarrier;
import com.youngtechcr.www.product.image.ProductImage;
import com.youngtechcr.www.http.ResponseEntityUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "/products")
public class ProductController {

    private final ProductService productService;
    private final ProductImageService productImageService;

    public ProductController(
            ProductService productService,
            ProductImageService productImageService) {
        this.productService = productService;
        this.productImageService = productImageService;
    }


    // TODO: Implement Pagination to improve clients
    @GetMapping
    public ResponseEntity<List<Product>> findSomeProucts(
            @RequestParam(value = "page", defaultValue = "0", required = false)  int pageNum,
            @RequestParam(value = "size", defaultValue = "10", required = false) int pageSize
    ) {
        List<Product> someProucts = productService
                .findSomeProducts(pageNum, pageSize)
                .getContent();
        return ResponseEntity.ok(someProucts);
    }

    @GetMapping(path = "/{attr}")
    public ResponseEntity<Product> findProductByAttr(
            @PathVariable("attr") String attr,
            @RequestParam("type") ProductAttribute type
    ) {
        Product prod = switch (type) {
            case ID -> productService.findById(Integer.parseInt(attr));
            case NAME -> productService.findByName(attr);
        };
        return ResponseEntity.ok(prod);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product productToBeCreated) {
        Product createdProduct = this.productService.createProduct(productToBeCreated);
        return ResponseEntityUtils.created(createdProduct);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Product> updateProductById(
            @PathVariable("id") Integer productId,
            @RequestBody Product productToBeUpdated
    ){
        Product updatedProduct = this.productService.updateProductById(productId,
                productToBeUpdated);
        return ResponseEntity.ok().body(updatedProduct);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Product> deleteProductById(@PathVariable("id") Integer productId) {
        this.productService.deleteProductById(productId);
        return ResponseEntity.noContent().build();
    }
    @PostMapping(path = "/{id}/images")
    public ResponseEntity<ProductImage> uploadImageByProductId(
            @PathVariable("id") Integer productId,
            @RequestPart(name = "file-data") MultipartFile imageToBeUploaded,
            @RequestParam(value = "main", required = false) boolean main
    ) {
        ProductImage uploadedImage = productImageService.uploadProductImageByProduct(
                productId, imageToBeUploaded, main);
        return ResponseEntityUtils.created(uploadedImage);
    }

    @GetMapping(path = "/{id}/images")
    public ResponseEntity<?> downloadMainImageOrObtainImageMetaDataList(
            @PathVariable("id") Integer productId,
            @RequestParam(name = "main", required = false) boolean isMain) {
        if(isMain) {
            DualNameFileCarrier resourceWithSomeMetaData = productImageService.
                    downloadMainImageByProduct(productId);
            return ResponseEntityUtils.downloadedFileWithMetaDataCarrier(
                    resourceWithSomeMetaData);
        }
        List<ProductImage> imagesMetaData = productImageService
                .getProductImagesMetadataById(productId);
        return ResponseEntity.ok().body(imagesMetaData);
    }

    @GetMapping(path = "/{productId}/images/{imageId}")
    public ResponseEntity<Resource> downloadProductImage(
            @PathVariable Integer productId,
            @PathVariable("imageId") Integer productImageId
    ) {
        DualNameFileCarrier resourceWithSomeMetaData = productImageService
                .downloadImageByProduct(productId, productImageId);
        return ResponseEntityUtils.
                downloadedFileWithMetaDataCarrier(resourceWithSomeMetaData);
    }

    @DeleteMapping(path = "/{productId}/images/{imageId}")
    public ResponseEntity<ProductImage> deleteProductImageByProductId(
            @PathVariable Integer productId,
            @PathVariable("imageId") Integer productImageId
    ){
        productImageService.deleteImageByProduct(productId, productImageId);
        return ResponseEntity.noContent().build();
    }

}
