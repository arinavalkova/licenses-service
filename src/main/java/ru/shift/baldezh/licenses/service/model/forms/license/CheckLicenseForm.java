package ru.shift.baldezh.licenses.service.model.forms.license;

import ru.shift.baldezh.licenses.service.repository.model.LicenseEntity;

public class CheckLicenseForm {
    private LicenseEntity license;
    private String uniqueHardwareId;

    public LicenseEntity getLicense() {
        return license;
    }

    public void setLicense(LicenseEntity license) {
        this.license = license;
    }

    public String getUniqueHardwareId() {
        return uniqueHardwareId;
    }

    public void setUniqueHardwareId(String uniqueHardwareId) {
        this.uniqueHardwareId = uniqueHardwareId;
    }
}
