package com.youngtechcr.www.product;

import com.youngtechcr.www.brand.Brand;
import com.youngtechcr.www.validation.ValidationOption;

public final class ProductValidatorTestUtils {

    public static Product withAllValid() {
        Product product = new Product();
        product.setName("Keyboard #1");
        product.setStock(0);
        product.setDescription("Some description");
        product.setPrice(900);
        product.setDiscountPercentage(12f);

        product.setBrand(new Brand(1));

        return product;
    }


    public static Product withInvalidName(ValidationOption option){
        var product = withAllValid();
        String name = switch (option) {
            case EMPTY -> "";
            case TOO_SHORT -> "f";
            case TOO_LONG -> "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%";
            case NULL_OR_DEFAULT -> null;
        };
        product.setName(name);
        return product;
    }

    public static Product withInvalidStock(ValidationOption option){
        var product = withAllValid();
        int stock = switch (option) {
            case NULL_OR_DEFAULT ->  0;
            case TOO_LONG ->  999;
            case TOO_SHORT ->  -123;
            case EMPTY ->  0;
        };
        product.setStock(stock);
        return product;
    }

    public static Product withInvalidDescription(ValidationOption option) {
        var product = withAllValid();
        String description = switch (option) {
            case EMPTY -> "";
            case TOO_SHORT -> "";
            case TOO_LONG -> "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&" +
                    "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&" +
                    "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&" +
                    "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&" +
                    "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&" +
                    "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&" +
                    "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&" +
                    "&&&&&&";
            case NULL_OR_DEFAULT -> null;
        };
        product.setDescription(description);
        return product;
    }

    public static Product withInvalidPrice(ValidationOption option) {
        var product = withAllValid();
        float price = switch (option) {
            case EMPTY -> 0.0F;
            case TOO_SHORT -> -30.0F;
            case TOO_LONG -> 0.0F;
            case NULL_OR_DEFAULT -> 0.0F;
        };
        product.setPrice(price);
        return product;
    }

    public static Product withInvalidDiscount(ValidationOption option) {
        var product = withAllValid();
        float discount = switch (option) {
            case EMPTY -> 0.0F;
            case TOO_SHORT -> -50.0F;
            case TOO_LONG -> 150.0F;
            case NULL_OR_DEFAULT -> 0.0F;
        };
        product.setDiscountPercentage(discount);
        return product;
    }
}
