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
}
