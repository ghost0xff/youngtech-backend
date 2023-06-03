package com.youngtechcr.www.services.storage;

import com.youngtechcr.www.domain.storage.FileData;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface FileSystemStorageService<T> {

    T saveToFileSystem(String serverFileName, Path pathToBeSaved, MultipartFile fileToBeSaved);

    T saveToDataBase(T fileToBeSaved);
}
