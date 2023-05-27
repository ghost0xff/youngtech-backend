package com.youngtechcr.www.services.storage;

import com.youngtechcr.www.domain.storage.FileData;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService {

    public void saveToFileSystem(Path path, MultipartFile multipartFile);

    public String generateServerFileName(String originalName);

    public FileData saveToDataBase(FileData fileData);
    public FileData saveToFileSystem(FileData fileData);

    public FileData retrieveFromFileSystem(FileData fileData);
    public FileData retrieveFromDataBase(FileData fileData);

}
