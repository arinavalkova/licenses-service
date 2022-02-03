package ru.shift.baldezh.licenses.service.service;

import org.springframework.http.ResponseEntity;
import ru.shift.baldezh.licenses.service.model.forms.license.CheckLicenseForm;
import ru.shift.baldezh.licenses.service.model.forms.license.GetLicenseForm;
import ru.shift.baldezh.licenses.service.model.forms.license.GetLicenseListForm;
import ru.shift.baldezh.licenses.service.model.forms.license.NewLicenseForm;
import ru.shift.baldezh.licenses.service.repository.model.LicenseEntity;

import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.List;

public interface LicenseService {

    LicenseEntity generateLicense(NewLicenseForm form) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, SignatureException, InvalidKeyException;

    LicenseEntity findLicenseById(GetLicenseForm form, long licenseId) throws IOException;

    List<Long> getLicenseIds(GetLicenseListForm form);

    ResponseEntity<?> checkLicense(CheckLicenseForm form);

    List<LicenseEntity> getCloseToExpireLicenses();
}
