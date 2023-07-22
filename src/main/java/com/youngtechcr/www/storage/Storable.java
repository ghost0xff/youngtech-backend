package com.youngtechcr.www.storage;

public interface Storable {

    public String getOriginalName();
    public String getServerName();
    public String getRelativePath();
    public String getMimeType();
    public long getSizeInBytes();
}
