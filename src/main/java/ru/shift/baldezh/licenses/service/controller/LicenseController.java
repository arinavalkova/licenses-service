package ru.shift.baldezh.licenses.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shift.baldezh.licenses.service.model.LicenseCheckResponse;
import ru.shift.baldezh.licenses.service.model.forms.license.CheckLicenseForm;
import ru.shift.baldezh.licenses.service.model.forms.license.GetLicenseForm;
import ru.shift.baldezh.licenses.service.model.forms.license.GetLicenseListForm;
import ru.shift.baldezh.licenses.service.model.forms.license.NewLicenseForm;
import ru.shift.baldezh.licenses.service.repository.model.LicenseEntity;
import ru.shift.baldezh.licenses.service.service.LicenseService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.List;

@RestController
@RequestMapping("license")
public class LicenseController {

    private final LicenseService licenseService;

    public LicenseController(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @GetMapping("new")
    public ResponseEntity<LicenseEntity> getNewLicense(
            @RequestBody NewLicenseForm form,
            HttpServletResponse response) {
        try {
            LicenseEntity licenseEntity = licenseService.generateLicense(form);
            setFilenameHeader(licenseEntity, response);
            return ResponseEntity.ok(licenseEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("{licenseId}")
    public ResponseEntity<LicenseEntity> getLicenseById(
            @PathVariable("licenseId") long licenseId,
            @RequestBody GetLicenseForm form,
            HttpServletResponse response
    ) {
        try {
            LicenseEntity licenseEntity = licenseService.findLicenseById(form, licenseId);
            setFilenameHeader(licenseEntity, response);
            return ResponseEntity.ok(licenseEntity);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    private void setFilenameHeader(LicenseEntity license, HttpServletResponse response) {
        String fileName = license.getLicenseId() + "_license_for_" + license.getUserId();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + ".json\"");
    }

    @GetMapping("list")
    public List<Long> getLicenseById(
            @RequestBody GetLicenseListForm form
    ) {
        return licenseService.getLicenseIds(form);
    }

    @PostMapping("check")
    public ResponseEntity<?> checkLicense(@RequestBody CheckLicenseForm form) {
        return licenseService.checkLicense(form);
    }
}
