package ru.shift.baldezh.licenses.library;

import java.io.File;
import java.security.PublicKey;

public class LicenseChecker {

    public boolean isActivated(PublicKey publicKey, File license, String uniqueHardwareId) {
        return true;
    }
}
