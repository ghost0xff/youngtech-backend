package com.youngtechcr.www.services.storage;

import com.youngtechcr.www.domain.storage.FileData;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;

@Service
public class ProductImageStorageService implements StorageService {


    @Override
    public void saveToFileSystem(Path path, MultipartFile multipartFile) {

    }

    @Override
    public String generateServerFileName(String originalName) {
        return null;
    }

    @Override
    public FileData saveToDataBase(FileData fileData) {
        return null;
    }

    @Override
    public FileData saveToFileSystem(FileData fileData) {
        return null;
    }

    @Override
    public FileData retrieveFromFileSystem(FileData fileData) {
        return null;
    }

    @Override
    public FileData retrieveFromDataBase(FileData fileData) {
        return null;
    }
}
