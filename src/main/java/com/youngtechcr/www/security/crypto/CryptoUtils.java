package com.youngtechcr.www.security.crypto;

import com.youngtechcr.www.exceptions.InternalErrorMessages;
import com.youngtechcr.www.security.InternalSecurityMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public final class CryptoUtils {

    private static final Logger logger = LoggerFactory.getLogger(CryptoUtils.class);

    /*
    REMEMBER DUMBY ->
        - X509 for public keys
        - PKCS8 for private keys
    */
    public static RSAKey rsaKeyFromDerFile(Path path, AsymmetricKeyType type ) {
        try {
            byte[] keyBytes = Files.readAllBytes(path);
            EncodedKeySpec spec = switch (type) {
                case PRIVATE -> new PKCS8EncodedKeySpec(keyBytes);
                case PUBLIC -> new X509EncodedKeySpec(keyBytes);
            };
            KeyFactory kf = KeyFactory.getInstance(CommonAlgorithms.RSA);
            RSAKey generatedKey = switch (type) {
                case PRIVATE -> (RSAPrivateKey) kf.generatePrivate(spec);
                case PUBLIC -> (RSAPublicKey) kf.generatePublic(spec);
            };
            logger.trace(InternalSecurityMessages.SUCCESFULL_KEY_LOADING);
            return (RSAKey) generatedKey;

        } catch (IOException e) {
           logger.error(InternalErrorMessages.COULDNT_READ_KEY_FILE);
           throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            logger.error("Invalid algorithm provided for KeyFactory while trying to load key");
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            logger.error("Invalid key spec argumented to KeyFactory object while trying to load key");
            throw new RuntimeException(e);
        }
    }

    // Some quick testing because I'm to lazy to create a proper unit test :v
//    public static void main(String[] args) {
//       Path publicPath = Path.of("/youngtech/app/youngtech-crypto/rsa-backend-keys/public-key.der");
//       Path privatePath = Path.of("/youngtech/app/youngtech-crypto/rsa-backend-keys/private-key.der");
//
//       RSAPublicKey publicKey =
//               (RSAPublicKey) rsaKeyFromDerFile(publicPath, AsymmetricKeyType.PUBLIC);
//       RSAPrivateKey privateKey =
//               (RSAPrivateKey) rsaKeyFromDerFile(privatePath, AsymmetricKeyType.PRIVATE);
//
//       logger.info("Generated private and public keys from source ");
//       logger.info("private key -> " + String.valueOf(privateKey));
//       logger.info("public key -> " + String.valueOf(publicKey));
//    }

}



