package ru.shift.baldezh.licenses.service.model;

import ru.shift.baldezh.licenses.service.repository.model.LicenseEntity;

public class LicenseCheckResponse {
    private LicenseEntity license;
    private String uniqueHardwareId;
    private String sign;

    public LicenseCheckResponse() {
    }
    public LicenseCheckResponse(LicenseEntity license, String uniqueHardwareId, String sign) {
        this.license = license;
        this.uniqueHardwareId = uniqueHardwareId;
        this.sign = sign;
    }

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

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
