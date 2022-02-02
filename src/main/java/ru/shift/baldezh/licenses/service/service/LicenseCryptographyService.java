package ru.shift.baldezh.licenses.service.service;

import org.springframework.http.ResponseEntity;
import ru.shift.baldezh.licenses.service.model.LicenseCheckResponse;
import ru.shift.baldezh.licenses.service.repository.model.LicenseEntity;

public interface LicenseCryptographyService {
    void sign(LicenseEntity entity);
    ResponseEntity<LicenseCheckResponse> getCheckResponse(LicenseEntity entity);
}
