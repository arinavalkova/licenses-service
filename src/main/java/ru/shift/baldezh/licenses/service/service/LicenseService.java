package ru.shift.baldezh.licenses.service.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.shift.baldezh.licenses.service.model.LicenseCheckResponse;
import ru.shift.baldezh.licenses.service.model.forms.license.GetLicenseForm;
import ru.shift.baldezh.licenses.service.model.forms.license.GetLicenseListForm;
import ru.shift.baldezh.licenses.service.model.forms.license.NewLicenseForm;
import ru.shift.baldezh.licenses.service.repository.model.LicenseEntity;

import java.io.IOException;
import java.util.List;

public interface LicenseService {

    LicenseEntity generateLicense(NewLicenseForm form) throws IOException;

    LicenseEntity findLicenseById(GetLicenseForm form, long licenseId) throws IOException;

    List<Long> getLicenseIds(GetLicenseListForm form);

    ResponseEntity<LicenseCheckResponse> checkLicense(MultipartFile file) throws IOException;
}
