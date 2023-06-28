package com.youngtechcr.www.utils;

import com.youngtechcr.www.product.Product;
import com.youngtechcr.www.productimage.ProductImageMetaData;

import java.util.ArrayList;
import java.util.List;

public class DomainUtils {

    public static final Product PRODUCT_WITH_IMAGES_AND_MAIN_IMAGE = setProductWithImagesAndMainImage();
    public static final Product PRODUCT_WITH_IMAGES_AND_NO_MAIN_IMAGE = setProductWithImagesAndNoMainImage();
    public static final ProductImageMetaData MAIN_PRODUCT_IMAGE = getMainImage();

    private static ProductImageMetaData getMainImage() {
        var mainImage = new ProductImageMetaData();
        mainImage.setServerFileName("server_image_2");
        mainImage.setMain(true);
        return mainImage;
    }

    private static Product setProductWithImagesAndMainImage() {
        var productToBeTested = new Product();
        List<ProductImageMetaData> imageList = new ArrayList<>();
        var notMainImage = new ProductImageMetaData();
        notMainImage.setServerFileName("server_image_1");
        notMainImage.setMain(false);
        var mainImage = new ProductImageMetaData();
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
        List<ProductImageMetaData> imageList = new ArrayList<>();
        var notMainImage = new ProductImageMetaData();
        notMainImage.setServerFileName("server_image_1");
        notMainImage.setMain(false);
        var mainImage = new ProductImageMetaData();
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
