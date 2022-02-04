package ru.shift.baldezh.licenses.service.repository.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "hardware_id")
public class UniqueHardwareIdEntity implements Serializable {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "license_id", nullable = false)
    private LicenseEntity license;

    @Column(name="activated_unique_hardware_id")
    private String activatedUniqueHardwareId;

    public String getActivatedUniqueHardwareId() {
        return activatedUniqueHardwareId;
    }

    public void setActivatedUniqueHardwareId(String activatedUniqueHardwareId) {
        this.activatedUniqueHardwareId = activatedUniqueHardwareId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LicenseEntity getLicense() {
        return license;
    }

    public void setLicense(LicenseEntity license) {
        this.license = license;
    }
}
