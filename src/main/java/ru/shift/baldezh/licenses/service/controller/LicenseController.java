package ru.shift.baldezh.licenses.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.shift.baldezh.licenses.service.model.LicenseCheckResponse;
import ru.shift.baldezh.licenses.service.model.forms.license.GetLicenseForm;
import ru.shift.baldezh.licenses.service.model.forms.license.GetLicenseListForm;
import ru.shift.baldezh.licenses.service.model.forms.license.NewLicenseForm;
import ru.shift.baldezh.licenses.service.service.LicenseService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("license")
public class LicenseController {

    private final LicenseService licenseService;

    public LicenseController(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @GetMapping("new")
    public void getNewLicense(
            @RequestBody NewLicenseForm form,
            HttpServletResponse response) {
        try {
            response.setContentType("application/txt");
            String fileName = licenseService.generateLicense(form, response.getOutputStream());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + ".txt\"");
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("{licenseId}")
    public void getLicenseById(
            @PathVariable("licenseId") long licenseId,
            @RequestBody GetLicenseForm form,
            HttpServletResponse response
    ) {
        try {
            response.setContentType("application/txt");
            String fileName = licenseService.findLicenseById(form, licenseId, response.getOutputStream());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + ".txt\"");
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
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
        try {
            return ResponseEntity.ok(
                    licenseService.checkLicense(file.getInputStream())
            );
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
