package ru.shift.baldezh.licenses.service.repository.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

public class LicenseEntity {
    @Id
    @GeneratedValue
    private long licenseId;

    private Date creationDate;

    private Date expirationDate;



}
