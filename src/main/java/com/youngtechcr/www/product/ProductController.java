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
import java.util.Set;

@RestController
@RequestMapping(path = "/products")
public class ProductController {

    private final ProductService productService;
    private final ProductImageService imageService;

    public ProductController(
            ProductService productService,
            ProductImageService imageService) {
        this.productService = productService;
        this.imageService = imageService;
    }

    // SEARCH
    @GetMapping(path = "/search")
    public ResponseEntity<List<Product>> search(
            @RequestParam("q") String query
    ) {
        var prods = productService.search(query);
        return ResponseEntity.ok(prods);
    }


    // PRODUCTS
    @GetMapping
    public ResponseEntity<List<Product>> findSomeProucts(
            @RequestParam(value = "page", defaultValue = "0", required = false)  int pageNum,
            @RequestParam(value = "size", defaultValue = "10", required = false) int pageSize
    ) {
        List<Product> someProucts = productService.findSomeProducts(pageNum, pageSize);
        return ResponseEntity.ok(someProucts);
    }

    @GetMapping(path = "/{attr}/related")
    public ResponseEntity<Set<Product>> findRelatedProducts(
            @PathVariable("attr") String attr,
            @RequestParam(value = "type", defaultValue = "ID",required = false) ProductAttribute type,
            @RequestParam(value = "quantity", defaultValue = "10", required = false) int quantity
    ) {
        Product prod = switch (type) {
            case ID -> productService.findById(Integer.parseInt(attr));
            case NAME -> productService.findByName(attr);
        };

        var prods =  productService.relatedProducts(prod, quantity);
        return ResponseEntity.ok(prods);
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

    // IMAGES

    @PostMapping(path = "/{id}/images")
    public ResponseEntity<ProductImage> uploadImageByProductId(
            @PathVariable("id") Integer productId,
            @RequestPart(name = "file-data") MultipartFile imageToBeUploaded,
            @RequestParam(value = "main", required = false) boolean main
    ) {
        ProductImage uploadedImage = imageService.uploadProductImageByProduct(
                productId, imageToBeUploaded, main);
        return ResponseEntityUtils.created(uploadedImage);
    }

   @GetMapping(path = "/{id}/images/main")
   public ResponseEntity<ProductImage> findMainImage(@PathVariable int id) {
        return ResponseEntity.ok(imageService.obtainMainImage(id));
   }


    @GetMapping(path = "/{attr}/images")
    public ResponseEntity<?> downloadMainImageOrObtainImageMetaDataList(
            @PathVariable String attr,
            @RequestParam(name = "main", required = false) boolean isMain,
            @RequestParam(required = false, defaultValue = "ID") ProductAttribute type
    ) {
        Product product = switch (type) {
            case ID -> productService.findById(Integer.parseInt(attr));
            case NAME -> productService.findByName(attr);
        };
        if(isMain) {
            DualNameFileCarrier resourceWithSomeMetaData = imageService.
                    downloadMainImageByProduct(product);
            return ResponseEntityUtils.downloadedFileWithMetaDataCarrier(
                    resourceWithSomeMetaData);
        }
        return ResponseEntity.ok().body(product.getImages());
    }

    @GetMapping(path = "/{productId}/images/{imageId}")
    public ResponseEntity<Resource> downloadProductImage(
            @PathVariable Integer productId,
            @PathVariable("imageId") Integer productImageId
    ) {
        DualNameFileCarrier resourceWithSomeMetaData = imageService
                .downloadImageByProduct(productId, productImageId);
        return ResponseEntityUtils.
                downloadedFileWithMetaDataCarrier(resourceWithSomeMetaData);
    }

    @DeleteMapping(path = "/{productId}/images/{imageId}")
    public ResponseEntity<ProductImage> deleteProductImageByProductId(
            @PathVariable Integer productId,
            @PathVariable("imageId") Integer productImageId
    ){
        imageService.deleteImageByProduct(productId, productImageId);
        return ResponseEntity.noContent().build();
    }

}
