package ru.shift.baldezh.licenses.service.service.impl;

import ru.shift.baldezh.licenses.service.service.KeyPairService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class KeyPairServiceImpl implements KeyPairService {
    private final static String PUBLIC_FILE_NAME = "public.key";
    private final static String PRIVATE_FILE_NAME = "private.key";

    private PublicKey publicKey;
    private PrivateKey privateKey;

    public KeyPairServiceImpl() throws IOException, NoSuchAlgorithmException {
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
                FileOutputStream privateFileStream = new FileOutputStream(PRIVATE_FILE_NAME);
        ) {
            KeyPairGenerator generator = null;
            generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            KeyPair pair = generator.generateKeyPair();
            privateKey = pair.getPrivate();
            publicKey = pair.getPublic();
            publicFileStream.write(publicKey.getEncoded());
            privateFileStream.write(publicKey.getEncoded());
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

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        EncodedKeySpec privateKeySpec = new X509EncodedKeySpec(privateKeyBytes);

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
