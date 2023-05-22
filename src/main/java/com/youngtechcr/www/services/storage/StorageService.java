package com.youngtechcr.www.services.storage;

import com.youngtechcr.www.domain.storage.FileData;
import com.youngtechcr.www.domain.storage.ProductImageFileData;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    public void saveToFileSystem(MultipartFile multipartFile);

    public String generateServerFileName(String originalName);

    public FileData saveRecordToDataBase(FileData fileData);

    public FileData retrieveFromFileSystem(FileData fileData);

}
