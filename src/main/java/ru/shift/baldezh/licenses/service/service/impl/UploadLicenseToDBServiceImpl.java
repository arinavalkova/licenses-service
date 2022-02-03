package ru.shift.baldezh.licenses.service.service.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.shift.baldezh.licenses.service.repository.UploadLicenseInfoRepository;
import ru.shift.baldezh.licenses.service.repository.model.LicenseEntity;
import ru.shift.baldezh.licenses.service.repository.model.UploadLicenseInfoEntity;
import ru.shift.baldezh.licenses.service.repository.model.UserInfoEntity;
import ru.shift.baldezh.licenses.service.service.LicenseService;
import ru.shift.baldezh.licenses.service.service.UploadLicenseService;
import ru.shift.baldezh.licenses.service.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class UploadLicenseToDBServiceImpl implements UploadLicenseService {

    private final LicenseService licenseService;
    private final UserService userService;
    private UploadLicenseInfoRepository uploadLicenseInfoRepository;

    public UploadLicenseToDBServiceImpl(LicenseService licenseService, UserService userService) {
        this.licenseService = licenseService;
        this.userService = userService;
    }

    @Scheduled(cron = "${interval-in-cron}")
    @Override
    public void uploadLicense() {
        List<UploadLicenseInfoEntity> uploadLicenseInfoEntityList = new ArrayList<>();
        List<LicenseEntity> listOfCloseToExpireLicenses = licenseService.getCloseToExpireLicenses();
        for (LicenseEntity licenseEntity : listOfCloseToExpireLicenses) {
            UploadLicenseInfoEntity uploadLicenseInfoEntity = new UploadLicenseInfoEntity();
            uploadLicenseInfoEntity.setLicenseId(licenseEntity.getLicenseId());

            long userId = licenseEntity.getUserId();
            UserInfoEntity userInfoEntity = userService.getUserById(userId).get();
            uploadLicenseInfoEntity.setUserMail(userInfoEntity.getMail());

            uploadLicenseInfoEntityList.add(uploadLicenseInfoEntity);
        }
        uploadLicenseInfoRepository.saveAll(uploadLicenseInfoEntityList);
    }
}
