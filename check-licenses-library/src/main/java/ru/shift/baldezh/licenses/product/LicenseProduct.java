package ru.shift.baldezh.licenses.product;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import ru.shift.baldezh.licenses.library.LicenseChecker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class LicenseProduct {

    private static String CRYPTO_ALGORITHM = "RSA";
    private final static String PUBLIC_FILE_NAME = "public.key";
    private static PublicKey publicKey;
    private static String uniqueHardwareId;
    private static File license;

    public static void main(String[] args) {
        if (isLicenseValid()) {
            work();
        } else {
            buy();
        }
    }

    private static boolean isLicenseValid() {
        try {
            publicKey = getPublicKey();
            //here getting publicKey from file
            //here getting uniqueHardwareId
            //here getting license from file
            LicenseChecker licenseChecker = new LicenseChecker();
            return licenseChecker.isActivated(publicKey, license, uniqueHardwareId);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static PublicKey getPublicKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        File publicKeyFile = new File(PUBLIC_FILE_NAME);
        byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(CRYPTO_ALGORITHM);
        return keyFactory.generatePublic(publicKeySpec);
    }

    private static void work() {
        System.out.println("License is valid. Welcome!");
    }

    private static void buy() {
        System.out.println("Your license has expired. Buy it on our website");
    }
}
