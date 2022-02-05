package ru.shift.baldezh.licenses.service.repository.specification;

import ru.shift.baldezh.licenses.service.repository.model.LicenseEntity;

import java.util.HashMap;

public class LicenseSpecification extends EntitySpecification<LicenseEntity> {
    public LicenseSpecification(LicenseEntity license) {
        super(new HashMap<String, Object>() {{
            put("userId", license.getUserId());
            put("mail", license.getMail());
            put("licenseType", license.getLicenseType());
        }});
    }
}