package ru.shift.baldezh.licenses.service.service;

import ru.shift.baldezh.licenses.service.model.LicenseCheckResponse;
import ru.shift.baldezh.licenses.service.model.forms.license.GetLicenseForm;
import ru.shift.baldezh.licenses.service.model.forms.license.GetLicenseListForm;
import ru.shift.baldezh.licenses.service.model.forms.license.NewLicenseForm;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface LicenseService {
    /* Returns Filename for license file of type .txt */
    String generateLicense(NewLicenseForm form, OutputStream stream);

    /* Returns Filename for license file of type .txt */
    String findLicenseById(GetLicenseForm form, OutputStream stream);

    List<Long> getLicenseIds(GetLicenseListForm form);

    LicenseCheckResponse checkLicense(InputStream stream);
}
