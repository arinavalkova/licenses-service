package ru.shift.baldezh.licenses.service.service.impl;

import org.springframework.stereotype.Component;
import ru.shift.baldezh.licenses.service.repository.model.LicenseEntity;
import ru.shift.baldezh.licenses.service.service.LicenseValidator;

@Component
public class LicenseValidatorImpl implements LicenseValidator {
    @Override
    public boolean licenseValid(LicenseEntity licenseFromDataBase, LicenseEntity license) {
        if (licenseFromDataBase.getCreationDate().getTime() != license.getCreationDate().getTime()) return false;
        if (licenseFromDataBase.getExpirationDate().getTime() != license.getExpirationDate().getTime()) return false;
        if (!licenseFromDataBase.getMail().equals(license.getMail())) return false;
        if (licenseFromDataBase.getUserId() != license.getUserId()) return false;
        if (!licenseFromDataBase.getSign().equals(license.getSign())) return false;
        return true;
    }
}
