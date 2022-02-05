package ru.shift.baldezh.licenses.service.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import ru.shift.baldezh.licenses.service.repository.model.LicenseEntity;

import java.util.List;

public interface LicenseRepository extends CrudRepository<LicenseEntity, Long>, JpaSpecificationExecutor<LicenseEntity> {
    LicenseEntity findLicenseEntityByUserIdAndLicenseId(long userId, long licenseId);
    List<LicenseEntity> getAllByUserId(long userId);
}