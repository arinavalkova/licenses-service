package ru.shift.baldezh.licenses.service.model.forms.license;

import ru.shift.baldezh.licenses.service.model.LicenseType;

import java.util.Set;

public class NewLicenseForm {
    private int userId;

    private String mail;

    private LicenseType licenseType;

    private Integer count;

    private Set<Long> productIds;

    public Set<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(Set<Long> productIds) {
        this.productIds = productIds;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public LicenseType getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(LicenseType licenseType) {
        this.licenseType = licenseType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
