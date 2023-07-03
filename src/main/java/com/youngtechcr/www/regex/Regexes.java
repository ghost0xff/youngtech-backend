package com.youngtechcr.www.regex;

public final class Regexes {

    public static final String REGEX_USERNAME_PATTERN = "^(?=[\\S]{3,20}$)[^0-9][a-zA-Z0-9_@$!]+$";
    public static final String REGEX_PASSWORD_PATTERN = "(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[\\d])(?=.*?[^\\sa-zA-Z0-9]).{8,}";
    public static final String REGEX_EMAIL_PATTERN = "^(?=[\\S]{1,319}$)\\S+@\\S+\\.\\S+$";
    public static final String REGEX_PERSON_NAME_AND_LASTNAME_PATTERN = "^(?=[a-zA-Z]{3,20}$)[a-zA-Z]+$";

}