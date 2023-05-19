package com.youngtechcr.www.backend.exceptions.custom;

public class EmptyRepositoryException extends RuntimeException{

    private String description;
    private String customErrorCode = "1";

    public EmptyRepositoryException(String exceptionMessage) {
        super(exceptionMessage);
        this.description = exceptionMessage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCustomErrorCode() {
        return customErrorCode;
    }

    public void setCustomErrorCode(String customErrorCode) {
        this.customErrorCode = customErrorCode;
    }
}
