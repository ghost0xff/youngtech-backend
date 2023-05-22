package com.youngtechcr.www.services.storage;

import com.youngtechcr.www.domain.Product;
import com.youngtechcr.www.domain.storage.FileData;
import com.youngtechcr.www.domain.storage.ProductImageFileData;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.UUID;

@Service
public class ProductImageFileDataStorageService implements StorageService {


    public void process(MultipartFile image, Product product){

    }

    @Override
    public void saveToFileSystem(MultipartFile productImageFile) {


        //TODO: Procesar y guardar archivo. Se deberian de crear directorios
        // dinamicamente. (Usar URLResource)
    }

    @Override
    public String generateServerFileName(String productId) {
        UUID newRandomUuid = UUID.randomUUID();
        String newServerFileName = productId + "-" + newRandomUuid;
        return newServerFileName;
    }

    @Override
    public FileData saveRecordToDataBase(FileData fileData) {
        return null;
    }

    @Override
    public FileData retrieveFromFileSystem(FileData fileData) {
        return null;
    }

    public Path existsInFileSystem(String imageServerName) {
        return null;
    }

}
