package ru.shift.baldezh.licenses.service.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.shift.baldezh.licenses.service.model.forms.license.NewLicenseForm;
import ru.shift.baldezh.licenses.service.service.LicenseService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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


}
