package ru.shift.baldezh.licenses.service.repository.model;

import ru.shift.baldezh.licenses.service.model.LicenseType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "license")
public class LicenseEntity {
    @Id
    @GeneratedValue
    private long licenseId;

    @Column(name = "creation_date", nullable = false)
    private Date creationDate;

    @Column(name = "expiration_date", nullable = false)
    private Date expirationDate;

    @Column(length = 2048, name = "sign", nullable = false)
    private String sign;

    @Column(name = "mail", nullable = false)
    private String mail;

    @JoinColumn(name="user_id")
    private long userId;

    @Column(name="activation_count", nullable = false)
    private int activationCount = 0;

    @Column(name="activated_unique_hardware_id")
    private String activatedUniqueHardwareId;

    @Column(name="license_type", nullable = false)
    private LicenseType licenseType;

    public boolean activate(String uniqueHardwareId) {
        int count = licenseType.getCount();
        if (activationCount < count) {
            activationCount++;
            activatedUniqueHardwareId = uniqueHardwareId;
            return true;
        }
        return uniqueHardwareId.equals(activatedUniqueHardwareId);
    }

    public LicenseType getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(LicenseType licenseType) {
        this.licenseType = licenseType;
    }

    public String getActivatedUniqueHardwareId() {
        return activatedUniqueHardwareId;
    }

    public void setActivatedUniqueHardwareId(String activatedUniqueHardwareId) {
        this.activatedUniqueHardwareId = activatedUniqueHardwareId;
    }

    public int getActivationCount() {
        return activationCount;
    }

    public void setActivationCount(int activationCount) {
        this.activationCount = activationCount;
    }

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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
