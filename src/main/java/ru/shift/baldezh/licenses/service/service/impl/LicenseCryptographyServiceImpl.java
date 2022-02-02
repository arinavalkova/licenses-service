package ru.shift.baldezh.licenses.service.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.shift.baldezh.licenses.service.model.LicenseCheckResponse;
import ru.shift.baldezh.licenses.service.repository.model.LicenseEntity;
import ru.shift.baldezh.licenses.service.service.LicenseCryptographyService;

@Component
public class LicenseCryptographyServiceImpl implements LicenseCryptographyService {
    @Override
    public void sign(LicenseEntity entity) {
        entity.setSign("TODO"); //TODO
    }

    @Override
    public ResponseEntity<LicenseCheckResponse> getCheckResponse(LicenseEntity entity) {
        return ResponseEntity.ok().build(); //TODO
    }
}
