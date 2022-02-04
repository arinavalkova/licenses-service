package ru.shift.baldezh.licenses.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.shift.baldezh.licenses.library.LicenseChecker;
import ru.shift.baldezh.licenses.library.ServerResponseBody;
import ru.shift.baldezh.licenses.service.repository.model.LicenseEntity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class LicenseProduct {

    private static final String CRYPTO_ALGORITHM = "RSA";
    private static final String PUBLIC_FILE_NAME = "public.key";
    private static final String LICENSE_FILE_NAME = "license.json";


    private static PublicKey publicKey;
    private static String uniqueHardwareId;
    private static LicenseEntity licenseEntity;

    public static void main(String[] args) {
        ServerResponseBody serverResponseBody = isLicenseValid(args);
        if (serverResponseBody == ServerResponseBody.ACTIVATED) {
            work();
        } else if (serverResponseBody == ServerResponseBody.INTERNAL_SERVER_ERROR) {
            System.out.println("Server error");
        } else if (serverResponseBody == ServerResponseBody.LICENSE_EXPIRED) {
            System.out.println("Your license has expired. Buy it on our website");
        } else if (serverResponseBody == ServerResponseBody.LICENSE_ALREADY_ACTIVATED) {
            System.out.println("Your license has already activated. Buy another one on our website");
        } else if (serverResponseBody == ServerResponseBody.LICENSE_NOT_EXIST) {
            System.out.println("License not exists");
        } else if (serverResponseBody == ServerResponseBody.BAD_DECODE_LICENSE) {
            System.out.println("Warning! Bad decoded license");
        }
    }

    private static ServerResponseBody isLicenseValid(String[] args) {
        try {
            publicKey = findPublicKey();
            uniqueHardwareId = args[0];
            licenseEntity = findLicenseEntity();

            LicenseChecker licenseChecker = new LicenseChecker();
            return licenseChecker.isActivated(publicKey, licenseEntity, uniqueHardwareId);

        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException
                | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
            return ServerResponseBody.INTERNAL_SERVER_ERROR;
        }
    }

    private static LicenseEntity findLicenseEntity() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(Paths.get(LICENSE_FILE_NAME).toFile(), LicenseEntity.class);
    }

    private static PublicKey findPublicKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        File publicKeyFile = new File(PUBLIC_FILE_NAME);
        byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(CRYPTO_ALGORITHM);
        return keyFactory.generatePublic(publicKeySpec);
    }

    private static void work() {
        System.out.println("License is valid. Welcome!");
    }
}
