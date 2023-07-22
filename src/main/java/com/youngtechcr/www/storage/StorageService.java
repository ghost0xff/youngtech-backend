package com.youngtechcr.www.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface StorageService<StorableT extends Storable, RelatedT> {
     StorableT store(RelatedT related, MultipartFile file);
     Resource obtain(StorableT storable);
     void dump(StorableT storable);

}
