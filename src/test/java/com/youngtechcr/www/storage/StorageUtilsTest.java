package com.youngtechcr.www.storage;

import com.youngtechcr.www.regex.RegexService;
import com.youngtechcr.www.regex.Regexes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class StorageUtilsTest {
    private static final Logger log = LoggerFactory.getLogger(StorageUtilsTest.class);
    private RegexService regexService;

    @BeforeEach
    void setUp(){
        this.regexService = new RegexService();
    }

    @Test
    @DisplayName("Should generate valid server file name")
    void given_Params_Should_Create_Valid_Server_File_Name(){

        String serverName = StorageUtils.generateServerName( 1,new MockMultipartFile("file.txt",
                (byte[])null), FileType.IMAGE);
        log.info("Generated server file name -> " + serverName);
         assertTrue(
            regexService.matches(Regexes.SERVER_FILENAME_PATTERN, serverName)
         );
    }
}