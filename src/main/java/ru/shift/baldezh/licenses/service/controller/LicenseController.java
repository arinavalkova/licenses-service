package ru.shift.baldezh.licenses.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.shift.baldezh.licenses.service.model.LicenseCheckResponse;
import ru.shift.baldezh.licenses.service.model.forms.license.GetLicenseForm;
import ru.shift.baldezh.licenses.service.model.forms.license.GetLicenseListForm;
import ru.shift.baldezh.licenses.service.model.forms.license.NewLicenseForm;
import ru.shift.baldezh.licenses.service.repository.model.LicenseEntity;
import ru.shift.baldezh.licenses.service.service.LicenseService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
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
            String fileName = licenseEntity.getLicenseId() + "_license_for_" + form.getUserId();
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + ".json\"");

            return ResponseEntity.ok(licenseEntity);
        } catch (IOException e) {
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
            String fileName = licenseEntity.getLicenseId() + "_license_for_" + form.getUserId();
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + ".json\"");
            return ResponseEntity.ok(licenseEntity);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("list")
    public List<Long> getLicenseById(
            @RequestBody GetLicenseListForm form
    ) {
        return licenseService.getLicenseIds(form);
    }

    @PostMapping("check")
    public ResponseEntity<LicenseCheckResponse> checkLicense(
            @RequestParam("file") MultipartFile file
    ) {
        return ResponseEntity.ok(
                licenseService.checkLicense(file)
        );
    }
}
