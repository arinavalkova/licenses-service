package ru.shift.baldezh.licenses.service.repository;

import org.springframework.data.repository.CrudRepository;
import ru.shift.baldezh.licenses.service.repository.model.LicenseEntity;

public interface LicenseRepository extends CrudRepository<LicenseEntity, Long> {
    LicenseEntity findLicenseEntityByUserIdAndLicenseId(long userId, long licenseId);
}
