package com.youngtechcr.www.storage;

public interface FileMetaData {

    public String getOriginalFileName();
    public void setOriginalFileName(String originalFileName);
    public String getServerFileName();
    public void setServerFileName(String serverFileName);
    public String getRelativePath();
    public void setRelativePath(String relativePath);
    public String getMimeType();
    public void setMimeType(String mimeType);
    public long getSizeInBytes();
    public void setSizeInBytes(long sizeInBytes);
}
