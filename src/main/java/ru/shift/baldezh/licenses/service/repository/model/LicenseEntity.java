package ru.shift.baldezh.licenses.service.repository.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

public class LicenseEntity {
    @Id
    @GeneratedValue
    private long licenseId;

    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "expiration_date", nullable = false)
    private Date expirationDate;

    @Column(name = "sign", nullable = false)
    private String sign;

    @Column(name = "mail", nullable = false)
    private String mail;

    public long getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(long licenseId) {
        this.licenseId = licenseId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
