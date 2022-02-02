package ru.shift.baldezh.licenses.service.service.impl;

import org.springframework.stereotype.Component;
import ru.shift.baldezh.licenses.service.model.LicenseCheckResponse;
import ru.shift.baldezh.licenses.service.model.forms.license.GetLicenseForm;
import ru.shift.baldezh.licenses.service.model.forms.license.GetLicenseListForm;
import ru.shift.baldezh.licenses.service.model.forms.license.NewLicenseForm;
import ru.shift.baldezh.licenses.service.service.LicenseService;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class LicenseServiceImpl implements LicenseService {
    @Override
    public String generateLicense(NewLicenseForm form, OutputStream stream) {
        /* TODO: implement */
        return "license";
    }

    @Override
    public String findLicenseById(GetLicenseForm form, long licenseId, OutputStream stream) {
        /* TODO: implement */
        return "license";
    }

    @Override
    public List<Long> getLicenseIds(GetLicenseListForm form) {
        return new ArrayList<>();
    }

    @Override
    public LicenseCheckResponse checkLicense(InputStream stream) {
        return new LicenseCheckResponse();
    }
}
