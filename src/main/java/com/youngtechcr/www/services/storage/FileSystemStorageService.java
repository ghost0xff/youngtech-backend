package com.youngtechcr.www.services.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface  FileSystemStorageService<T> {

    T saveToFileSystem(String serverFileName, Path pathToBeSaved, MultipartFile fileToBeSaved);

    Resource retrieveFromFileSystem(Path absoluteFilePath);

    void removeFromFileSystemAndDataBase(Path absoluteFilePathToBeEliminated, T elementToBeDeleted);

    T saveToDataBase(T fileToBeSaved);

}
