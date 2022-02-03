package ru.shift.baldezh.licenses.service.repository.model;

import javax.persistence.*;

@Entity
@Table(name = "upload_license_info")
public class UploadLicenseInfoEntity {

    @Id
    @GeneratedValue
    private long uploadLicenseInfoId;

    @Column(name = "license_id", nullable = false)
    private long licenseId;

    @Column(name = "user_mail", nullable = false)
    private String userMail;

    public long getUploadLicenseInfoId() {
        return uploadLicenseInfoId;
    }

    public void setUploadLicenseInfoId(long uploadLicenseInfoId) {
        this.uploadLicenseInfoId = uploadLicenseInfoId;
    }

    public long getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(long licenseId) {
        this.licenseId = licenseId;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }
}
