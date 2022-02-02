package ru.shift.baldezh.licenses.service.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

public interface KeyPairService {
    void recreateKeyPair() throws NoSuchAlgorithmException, IOException;
    PublicKey getPublicKey();
    PrivateKey getPrivateKey();
}
