package ru.shift.baldezh.licenses.service.service.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.shift.baldezh.licenses.service.service.KeyPairService;

import javax.crypto.KeyGenerator;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Component
public class KeyPairServiceImpl implements KeyPairService {
    private final static String PUBLIC_FILE_NAME = "public.key";
    private final static String PRIVATE_FILE_NAME = "private.key";

    private final String cryptoAlgorithm;

    private PublicKey publicKey;
    private PrivateKey privateKey;

    public KeyPairServiceImpl(
            @Qualifier("CRYPTO_ALGORITHM") String cryptoAlgorithm
    ) throws IOException, NoSuchAlgorithmException {
        this.cryptoAlgorithm = cryptoAlgorithm;
        if (doKeyFilesExist()) {
            try {
                readKeys();
                return;
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }
        }
        generateKeys();
    }

    private void generateKeys() throws NoSuchAlgorithmException, IOException {
        try (
                FileOutputStream publicFileStream = new FileOutputStream(PUBLIC_FILE_NAME);
                FileOutputStream privateFileStream = new FileOutputStream(PRIVATE_FILE_NAME)
        ) {
            KeyPairGenerator generator = KeyPairGenerator.getInstance(cryptoAlgorithm);
            generator.initialize(2048);
            KeyPair pair = generator.generateKeyPair();
            privateKey = pair.getPrivate();
            publicKey = pair.getPublic();
            publicFileStream.write(publicKey.getEncoded());
            privateFileStream.write(privateKey.getEncoded());
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private void readKeys() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        File publicKeyFile = new File(PUBLIC_FILE_NAME);
        byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());

        File privateKeyFile = new File(PRIVATE_FILE_NAME);
        byte[] privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath());

        KeyFactory keyFactory = KeyFactory.getInstance(cryptoAlgorithm);
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);

        publicKey = keyFactory.generatePublic(publicKeySpec);
        privateKey = keyFactory.generatePrivate(privateKeySpec);
    }

    private boolean doKeyFilesExist() {
        return checkIfFileExist(PUBLIC_FILE_NAME) &&
                checkIfFileExist(PRIVATE_FILE_NAME);
    }

    private boolean checkIfFileExist(String fileName) {
        File file = new File(fileName);
        return file.exists() && file.isFile();
    }


    @Override
    public void recreateKeyPair() throws NoSuchAlgorithmException, IOException {
        generateKeys();
    }

    @Override
    public PublicKey getPublicKey() {
        return publicKey;
    }

    @Override
    public PrivateKey getPrivateKey() {
        return privateKey;
    }
}
