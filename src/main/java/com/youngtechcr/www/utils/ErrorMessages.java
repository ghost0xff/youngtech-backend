package com.youngtechcr.www.utils;

public final class ErrorMessages {
    public static final String NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND = "No element with the requested id was found";
    public static final String PROVIDED_IDS_DONT_MATCH = "Can't proceed with operation because path/url id and element id provided don't match";
    public static final String CANT_CREATE_DUPLICATE_NAME = "Can't create element with requested name because it already exists another element with the name provided";
    public static final String INVALID_PRODUCT_REASON_BRAND = "Invalid brand value: brand provided doesn't exist or it wasn't provided at all, the id should be provided";
    public static final String INVALID_PRODUCT_REASON_CATEGORY = "Invalid category value: category provided doesn't exist or it wasn't provided at all, the id should be provided";
    public static final String INVALID_PRODUCT_REASON_SUBCATEGORY = "Invalid subcategory value: subcategory provided doesn't exist or it wasn't provided at all, the id should be provided";
    public static final String INVALID_PRODUCT_REASON_DISCOUNT_PERCENTAGE = "Invalid discount percentage value: discount should be between 0 and 100";
    public static final String INVALID_PRODUCT_REASON_PRICE = "Invalid price value: price should be greater or equal to 0";
    public static final String INVALID_PRODUCT_REASON_DESCRIPTION = "Invalid description length: the description must be less than or equal to 255 characters in length";
    public static final String INVALID_PRODUCT_REASON_STOCK = "Invalid stock: the stock must be more than or equal to 0";
    public static final String INVALID_PRODUCT_REASON_NAME = "Invalid name: the name must be less than or equal to 45 characters in length";
    public static final String CANT_CREATE_DUPLICATE_ID = "Can't create element with requested id it already exists another element with the id provided";
    // Hierarchy of elements should be explained in the official documentation of this application
    public static final String REQUESTED_CHILD_ELEMENT_DOESNT_EXIST = "Requested child element doesn't match for requested parent element";

}
