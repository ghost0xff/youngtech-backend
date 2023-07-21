package com.youngtechcr.www.regex;

public final class Regexes {

    // User
    public static final String USERNAME_PATTERN = "^(?=[\\S]{3,20}$)[^0-9][a-zA-Z0-9_@$!]+$";
    public static final String PASSWORD_PATTERN = "(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[\\d])(?=.*?[^\\sa-zA-Z0-9]).{8,}";
    public static final String EMAIL_PATTERN = "^(?=[\\S]{1,319}$)\\S+@\\S+\\.\\S+$";

    //Person
    public static final String PERSON_NAME_AND_LASTNAME_PATTERN = "^(?=[^\\s].+[^\\s]$)[a-zA-Z\\s]{3,20}$"; // not chars at start nor end && just alphabetic chars


    //Product
    public static final String PRODUCT_NAME_PATTERN = "^(?=[^\\s].+[^\\s]$).{1,60}$";
    public static final String PRODUCT_DESCRIPTION_PATTERN = "^.{1,255}$";
}