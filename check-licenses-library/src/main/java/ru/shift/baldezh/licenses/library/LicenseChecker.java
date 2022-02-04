package ru.shift.baldezh.licenses.library;

import ru.shift.baldezh.licenses.service.repository.model.LicenseEntity;

import java.security.PublicKey;

public class LicenseChecker {

    public boolean isActivated(PublicKey publicKey, LicenseEntity licenseEntity, String uniqueHardwareId) {

        return true;
    }
}
