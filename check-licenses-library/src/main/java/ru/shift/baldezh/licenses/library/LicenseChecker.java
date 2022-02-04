package ru.shift.baldezh.licenses.library;

import ru.shift.baldezh.licenses.service.model.forms.license.CheckLicenseForm;

import java.security.PublicKey;

public class LicenseChecker {

    public boolean isActivated(PublicKey publicKey, CheckLicenseForm checkLicenseForm, String uniqueHardwareId) {
        return true;
    }
}
