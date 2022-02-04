package ru.shift.baldezh.licenses.product;

import ru.shift.baldezh.licenses.library.LicenseChecker;

import java.io.File;
import java.security.PublicKey;

public class LicenseProduct {

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
        //here getting publicKey from file
        //here getting uniqueHardwareId
        //here getting license from file
        LicenseChecker licenseChecker = new LicenseChecker();
        return licenseChecker.isActivated(publicKey, license, uniqueHardwareId);
    }

    private PublicKey getPublicKey() {

    }

    private static void work() {
        System.out.println("License is valid. Welcome!");
    }

    private static void buy() {
        System.out.println("Your license has expired. Buy it on our website");
    }
}
