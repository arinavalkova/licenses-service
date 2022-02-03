package ru.shift.baldezh.licenses.service.service.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.shift.baldezh.licenses.service.service.LicenseService;
import ru.shift.baldezh.licenses.service.service.UploadLicenseService;
import ru.shift.baldezh.licenses.service.service.UserService;

@Service
public class UploadLicenseToDBServiceImpl implements UploadLicenseService {

    private LicenseService licenseService;
    private UserService userService;

    public UploadLicenseToDBServiceImpl(LicenseService licenseService, UserService userService) {
        this.licenseService = licenseService;
        this.userService = userService;
    }

    @Scheduled(cron = "${interval-in-cron}")
    @Override
    public void uploadLicense() {
        System.out.println("Hello World");
    }
}
