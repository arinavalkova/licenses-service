package ru.shift.baldezh.licenses.service.repository;

import org.springframework.data.repository.CrudRepository;
import ru.shift.baldezh.licenses.service.repository.model.UploadLicenseInfoEntity;

public interface UploadLicenseInfoRepository extends CrudRepository<UploadLicenseInfoEntity, Long> {
}
