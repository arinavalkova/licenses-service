package ru.shift.baldezh.licenses.service.service;

import ru.shift.baldezh.licenses.service.model.LicenseCheckResponse;
import ru.shift.baldezh.licenses.service.model.forms.license.CheckLicenseForm;
import ru.shift.baldezh.licenses.service.repository.model.LicenseEntity;

import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

public interface LicenseCryptographyService {
    void sign(LicenseEntity entity) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, SignatureException;
    LicenseCheckResponse getCheckResponse(CheckLicenseForm form);
}
