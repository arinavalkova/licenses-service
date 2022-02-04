package ru.shift.baldezh.licenses.service.service;

import ru.shift.baldezh.licenses.service.repository.model.LicenseEntity;

public interface LicenseValidator {
    boolean licenseValid(LicenseEntity licenseFromDataBase, LicenseEntity license);
}
