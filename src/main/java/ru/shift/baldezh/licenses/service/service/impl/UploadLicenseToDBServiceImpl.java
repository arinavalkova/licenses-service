package ru.shift.baldezh.licenses.service.service.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.shift.baldezh.licenses.service.service.UploadLicenseService;

@Service
public class UploadLicenseToDBServiceImpl implements UploadLicenseService {

    @Scheduled(cron = "${interval-in-cron}")
    @Override
    public void uploadLicense() {
        System.out.println("Hello World");
    }
}
