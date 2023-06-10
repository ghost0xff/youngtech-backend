package com.youngtechcr.www.utils;

import com.youngtechcr.www.domain.Product;
import com.youngtechcr.www.domain.storage.ProductImageFileData;

import java.util.ArrayList;
import java.util.List;

public class TestingDomainUtils {

    public static  final Product PRODUCT_WITH_IMAGES_AND_MAIN_IMAGE = setProductWithImagesAndMainImage();
    public static  final Product PRODUCT_WITH_IMAGES_AND_NO_MAIN_IMAGE = setProductWithImagesAndNoMainImage();

    private static Product setProductWithImagesAndMainImage() {
        var productToBeTested = new Product();
        List<ProductImageFileData> imageList = new ArrayList<>();
        var notMainImage = new ProductImageFileData();
        notMainImage.setServerFileName("server_image_1");
        notMainImage.setMain(false);
        var mainImage = new ProductImageFileData();
        mainImage.setServerFileName("server_image_2");
        mainImage.setMain(true);
        imageList.add(notMainImage);
        imageList.add(notMainImage);
        imageList.add(notMainImage);
        imageList.add(notMainImage);
        imageList.add(notMainImage);
        imageList.add(mainImage);
        imageList.add(notMainImage);
        productToBeTested.setImageList(imageList);
        return productToBeTested;
    }

    public static Product setProductWithImagesAndNoMainImage() {
        var productToBeTested = new Product();
        List<ProductImageFileData> imageList = new ArrayList<>();
        var notMainImage = new ProductImageFileData();
        notMainImage.setServerFileName("server_image_1");
        notMainImage.setMain(false);
        var mainImage = new ProductImageFileData();
        mainImage.setServerFileName("server_image_2");
        mainImage.setMain(true);
        imageList.add(notMainImage);
        imageList.add(notMainImage);
        imageList.add(notMainImage);
        imageList.add(notMainImage);
        imageList.add(notMainImage);
        imageList.add(notMainImage);
        imageList.add(notMainImage);
        productToBeTested.setImageList(imageList);
        return productToBeTested;
    }
}
