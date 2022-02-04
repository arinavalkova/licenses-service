package ru.shift.baldezh.licenses.service.service;

import ru.shift.baldezh.licenses.service.model.forms.license.CheckLicenseForm;
import ru.shift.baldezh.licenses.service.repository.model.LicenseEntity;

public interface SignatureDataBuilder {
    String getLicenseString(LicenseEntity entity);
    String getResponseString(LicenseEntity entity, String hardwareId);
}
