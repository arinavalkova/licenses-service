package ru.shift.baldezh.licenses.service.service.impl;

import org.springframework.stereotype.Component;
import ru.shift.baldezh.licenses.service.model.forms.license.NewLicenseForm;
import ru.shift.baldezh.licenses.service.service.LicenseFileGeneratorService;

import java.io.OutputStream;

@Component
public class LicenseFileGeneratorServiceImpl implements LicenseFileGeneratorService {
    @Override
    public String generateLicense(NewLicenseForm form, OutputStream stream) {
        /* TODO: implement */
        return "license";
    }
}
