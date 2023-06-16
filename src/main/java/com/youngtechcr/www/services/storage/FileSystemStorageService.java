package com.youngtechcr.www.services.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface  FileSystemStorageService<T> {

    T saveToFileSystem(String serverFileName, Path pathToBeSaved, MultipartFile fileToBeSaved);

    Resource retrieveFromFileSystem(Path absoluteFilePath);

    void removeFromFileSystem(Path absoluteFilePathToBeEliminated);

    T saveToDataBase(T fileToBeSaved);

    void removeFromDataBase(Integer elementId);
}
