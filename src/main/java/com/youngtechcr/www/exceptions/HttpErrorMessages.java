package com.youngtechcr.www.exceptions;

public final class HttpErrorMessages {

    //REGEX EXCEPTION
    public static final String NULL_REGEX_PATTERN_OR_TEXT_VALUE= "An internal error occurred while matching/validating element or value";


    // PRODUCT VALIDATION
    public static final String INVALID_PRODUCT = "Error on product creation: product element in request body is not valid.";
    public static final String INVALID_PRODUCT_REASON_DISCOUNT_PERCENTAGE = "Invalid discount percentage value: discount should be between 1 and 99.";
    public static final String INVALID_PRODUCT_REASON_PRICE = "Invalid price: must be greater to 0.";
    public static final String INVALID_PRODUCT_REASON_DESCRIPTION = "Invalid description length: the description must be betweem 1 - 255 characters in length.";
    public static final String INVALID_PRODUCT_REASON_STOCK = "Invalid stock: the stock must be more than or equal to 0.";
    public static final String INVALID_PRODUCT_REASON_NAME = "Invalid name: the name must be less than or equal to 60 characters in length && can't be null.";
    public static final String INVALID_PRODUCT_REASON_BRAND = "Invalid product brand value: brand provided doesn't exist or it wasn't provided at all (null), the id should be provided.";
    public static final String INVALID_PRODUCT_REASON_CATEGORY = "Invalid product category: category provided doesn't exist or it wasn't provided at all, the id should be provided.";
    public static final String INVALID_PRODUCT_REASON_SUBCATEGORY = "Invalid product subcategory: subcategory provided doesn't exist or it wasn't provided at all, the id should be provided.";

    //  USER VALIDATION
    public static final String INVALID_USER_REASON_ALREADY_EXISTS_EMAIL = "Can't create user with requested email because email already exists";
    public static final String INVALID_USER_REASON_PASSWORD = "Invalid user: password must match requierements";
    public static final String INVALID_USER_REASON_EMAIL = "Invalid user: email must match requierements";

    // PERSON VALIDATION
    public static final String INVALID_PERSON_REASON_NAME = "Invalid name: name must match requirements";
    public static final String INVALID_PERSON_REASON_LASTNAME = "Invalid lastname: lastname must match requirements";
    public static final String INVALID_PERSON_REASON_AGE = "Invalid age: age must be between 1 - 99. Numbers 0 && 100 aren't valid";
    public static final String INVALID_PERSON_REASON_NULL = "Invalid person: person must not be null";


    // ELEMENT VALIDATION

    public static final String PROVIDED_IDS_DONT_MATCH = "Can't proceed with operation because path/url id and element id provided don't match.";
    public static final String NO_ELEMENT_WITH_THE_REQUESTED_ID_WAS_FOUND = "No element with the requested id was found.";
    public static final String NO_ELEMENT_WITH_THE_REQUESTED_NAME_WAS_FOUND = "No element with the requested name was found.";
    public static final String CANT_CREATE_DUPLICATE_NAME = "Can't create element with requested name because it already exists another element with the name provided.";
    public static final String CANT_PROVIDE_ID_DURING_ELEMENT_CREATION = "Can't provide id during element creation";
    public static final String NO_ELEMENTS_FOUND_IN_SERVER = "No elements for requested type found in server"; // Hierarchy of elements should be explained in the official documentation of this application
    public static final String NO_MAIN_ELEMENT_WAS_FOUND= "No main element was found.";


    // HTTP REQUEST ERRORS
    public static final String REQUESTED_CHILD_ELEMENT_DOESNT_EXIST = "Requested child element doesn't match for requested parent element.";
    public static final String REQUESTED_TOO_MUCH_ENTITIES = "Requested too much elements. Elements requested during pagination mustn't surpass 1000 entities per page";


    // STORAGE ERRORS
    public static final String UNABLE_TO_UPLOAD_REQUESTED_FILE = "Couldn't store uploaded file due to unexpected problems on the server";
    public static final String UNABLE_TO_DOWNLOAD_REQUESTED_FILE = "Couldn't download file due to unexpected problems on the server.";
    public static final String UNABLE_TO_LOCATE_REQUESTED_FILE = "Couldn't locate requested file. File might not exist or might've been deleted";
    public static final String UNABLE_TO_DELETE_REQUESTED_FILE = "Couldn't delete requested file from server due to unexpected problems on the server";
    public static final String CANT_CREATE_DUPLICATE_MAIN_IMAGE = "Can't upload requested image as main because  it already exists another main image";


    // SERVER ERRORS
    public static final String FAILED_CONNECTION_EXTERNAL_SERVER =  "Couldn't validate external id token due to failed communication with external resource server";


    // SECURITY, OAUTH2.0, OIDC AND EIDC
    public static final String INVALID_EIDTE_TOKEN =  "Invalid external token provided, try refreshing your actual token or trying with another one";
    public static final String FAILED_GENERATE_TOKEN = "Failed to generate requested token";
}
