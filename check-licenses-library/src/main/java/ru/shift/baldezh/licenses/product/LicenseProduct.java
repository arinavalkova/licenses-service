package ru.shift.baldezh.licenses.product;

import java.security.PublicKey;

public class LicenseProduct {

    private PublicKey publicKey;
    private String uniqueHardwareId;

    public static void main(String[] args) {

        //here getting publicKey from file
        //here getting uniqueHardwareId

        if (isLicenseValid()) {
            work();
        } else {
            buy();
        }
    }

    public static boolean isLicenseValid() {
        return true;
    }

    public static void work() {
        System.out.println("License is valid. Welcome!");
    }

    public static void buy() {
        System.out.println("Your license has expired. Buy it on our website");
    }
}
