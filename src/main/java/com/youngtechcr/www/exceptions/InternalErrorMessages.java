package com.youngtechcr.www.exceptions;

public class InternalErrorMessages {

    //REGEX EXCEPTION
    public static final String CANT_MATCH_NULL_REGEX = "Provided regex pattern is null and can't be used to match text";
    public static final String CANT_MATCH_NULL_TEXT = "Provided text is null and can't be used to be matched against regex";

    //CRYPTOGRAPHIC ERRORS
    public static final String COULDNT_READ_KEY_FILE = "Couldn't read key file due to failed IO operations. Check if file exists or it's URI is malformed";
}
