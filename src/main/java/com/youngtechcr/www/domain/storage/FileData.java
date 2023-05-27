package com.youngtechcr.www.domain.storage;

public interface FileData {

    public String getOriginalFileName();
    public void setOriginalFileName(String originalFileName);
    public String getServerFileName();
    public void getServerFileName(String serverFileName);
    public String getRelativePath();
    public void setRelativePath(String relativePath);

}
