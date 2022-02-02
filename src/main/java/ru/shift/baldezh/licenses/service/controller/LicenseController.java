package ru.shift.baldezh.licenses.service.controller;

import org.springframework.web.bind.annotation.*;
import ru.shift.baldezh.licenses.service.model.forms.license.NewLicenseForm;
import ru.shift.baldezh.licenses.service.service.LicenseFileGeneratorService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("license")
public class LicenseController {

    private final LicenseFileGeneratorService licenseFileGeneratorService;

    public LicenseController(LicenseFileGeneratorService licenseFileGeneratorService) {
        this.licenseFileGeneratorService = licenseFileGeneratorService;
    }

    @GetMapping("new")
    public void getNewLicense(
            @RequestBody NewLicenseForm form,
            HttpServletResponse response) {
        try {
            response.setContentType("application/txt");
            String fileName = licenseFileGeneratorService.generateLicense(form, response.getOutputStream());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + ".txt\"");
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
