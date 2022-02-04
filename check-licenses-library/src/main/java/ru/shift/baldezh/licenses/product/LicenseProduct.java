package ru.shift.baldezh.licenses.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.shift.baldezh.licenses.library.LicenseChecker;
import ru.shift.baldezh.licenses.service.model.forms.license.CheckLicenseForm;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class LicenseProduct {

    private static final String CRYPTO_ALGORITHM = "RSA";
    private static final String PUBLIC_FILE_NAME = "public.key";
    private static final String LICENSE_FILE_NAME = "license.json";


    private static PublicKey publicKey;
    private static String uniqueHardwareId;
    private static CheckLicenseForm checkLicenseForm;

    public static void main(String[] args) {
        if (isLicenseValid(args)) {
            work();
        } else {
            buy();
        }
    }

    private static boolean isLicenseValid(String[] args) {
        try {
            publicKey = findPublicKey();
            uniqueHardwareId = args[0];
            checkLicenseForm = findCheckLicenseForm();

            LicenseChecker licenseChecker = new LicenseChecker();
            return licenseChecker.isActivated(publicKey, checkLicenseForm, uniqueHardwareId);

        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static CheckLicenseForm findCheckLicenseForm() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(Paths.get(LICENSE_FILE_NAME).toFile(), CheckLicenseForm.class);
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

    private static void buy() {
        System.out.println("Your license has expired. Buy it on our website");
    }
}
