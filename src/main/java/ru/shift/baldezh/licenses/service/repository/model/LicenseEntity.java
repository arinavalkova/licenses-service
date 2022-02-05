package ru.shift.baldezh.licenses.service.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.shift.baldezh.licenses.service.model.LicenseType;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

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

    @JoinColumn(name = "user_id")
    private long userId;

    @JsonIgnore
    @OneToMany(mappedBy = "license", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<UniqueHardwareIdEntity> activatedHardwareIds;

    @Column(name = "license_type", nullable = false)
    private LicenseType licenseType;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "license_product",
    joinColumns = @JoinColumn(name = "license_id"),
    inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> products;

    public boolean activate(String uniqueHardwareId) {
        int count = licenseType.getCount();
        int activationCount = activatedHardwareIds.size();
        boolean thisIdAlreadyActivated = activatedHardwareIds.stream()
                .anyMatch(e -> e.getActivatedUniqueHardwareId().equals(uniqueHardwareId));
        if (thisIdAlreadyActivated) return true;
        if (activationCount < count) {
            UniqueHardwareIdEntity entity = new UniqueHardwareIdEntity();
            entity.setActivatedUniqueHardwareId(uniqueHardwareId);
            entity.setLicense(this);
            activatedHardwareIds.add(entity);
            return true;
        }
        return false;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public LicenseType getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(LicenseType licenseType) {
        this.licenseType = licenseType;
    }

    public Set<UniqueHardwareIdEntity> getActivatedHardwareIds() {
        return activatedHardwareIds;
    }

    public void setActivatedHardwareIds(Set<UniqueHardwareIdEntity> activatedHardwareIds) {
        this.activatedHardwareIds = activatedHardwareIds;
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
