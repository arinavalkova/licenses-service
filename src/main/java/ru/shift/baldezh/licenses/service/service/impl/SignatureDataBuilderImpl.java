package ru.shift.baldezh.licenses.service.service.impl;

import org.springframework.stereotype.Component;
import ru.shift.baldezh.licenses.service.model.forms.license.CheckLicenseForm;
import ru.shift.baldezh.licenses.service.repository.model.LicenseEntity;
import ru.shift.baldezh.licenses.service.service.SignatureDataBuilder;

@Component
public class SignatureDataBuilderImpl implements SignatureDataBuilder {
    private static final String SEPARATOR = ";";

    @Override
    public String getLicenseString(LicenseEntity entity) {
        return entity.getLicenseId() + SEPARATOR +
                entity.getMail() + SEPARATOR +
                entity.getCreationDate().toString() + SEPARATOR +
                entity.getExpirationDate().toString();
    }

    @Override
    public String getResponseString(LicenseEntity entity, String hardwareId) {
        return getLicenseString(entity) + SEPARATOR + hardwareId;
    }
}
