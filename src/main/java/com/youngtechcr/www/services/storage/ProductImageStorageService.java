package com.youngtechcr.www.services.storage;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.security.Principal;

@Service
public class ProductImageStorageService {


    public void processAndSave(MultipartFile multipartFile) {
//        Path retrievedRelatedPath = Path.of()
        //TODO: Procesar y guardar archivo. Se deberian de crear directorios
        // dinamicamente. (Usar URLResource)
    }

    private String generateNewComputedName(String originalName) {
        //TODO: generar String para nombrar a archivo a la hora de guardarlo en
        // el sistema de archivos local basado en: idProduct + UUID

        return null;

    }
}
