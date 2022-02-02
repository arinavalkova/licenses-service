package ru.shift.baldezh.licenses.service.service;

import ru.shift.baldezh.licenses.service.model.forms.license.NewLicenseForm;

import java.io.OutputStream;

public interface LicenseFileGeneratorService {
    /* Returns Filename for license file of type .txt */
    String generateLicense(NewLicenseForm form, OutputStream stream);
}
