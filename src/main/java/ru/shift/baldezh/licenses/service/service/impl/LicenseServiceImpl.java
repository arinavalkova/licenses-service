package ru.shift.baldezh.licenses.service.service.impl;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.shift.baldezh.licenses.service.model.LicenseCheckResponse;
import ru.shift.baldezh.licenses.service.model.forms.license.GetLicenseForm;
import ru.shift.baldezh.licenses.service.model.forms.license.GetLicenseListForm;
import ru.shift.baldezh.licenses.service.model.forms.license.NewLicenseForm;
import ru.shift.baldezh.licenses.service.repository.LicenseRepository;
import ru.shift.baldezh.licenses.service.repository.model.LicenseEntity;
import ru.shift.baldezh.licenses.service.service.LicenseCryptographyService;
import ru.shift.baldezh.licenses.service.service.LicenseService;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LicenseServiceImpl implements LicenseService {

    private static final int EXPIRE_TIME_SPAN = 31;

    private final LicenseRepository licenseRepository;
    private final LicenseCryptographyService licenseCryptographyService;


    public LicenseServiceImpl(LicenseRepository licenseRepository, LicenseCryptographyService licenseCryptographyService) {
        this.licenseRepository = licenseRepository;
        this.licenseCryptographyService = licenseCryptographyService;
    }

    @Override
    public LicenseEntity generateLicense(NewLicenseForm form) {
        int userId = form.getUserId();

        Date currentDate = new Date();
        LicenseEntity licenseEntity = new LicenseEntity();
        licenseEntity.setCreationDate(currentDate);

        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DAY_OF_MONTH, EXPIRE_TIME_SPAN);
        licenseEntity.setExpirationDate(c.getTime());

        licenseCryptographyService.sign(licenseEntity);

        licenseEntity.setMail(form.getMail());

        licenseEntity.setUserId(userId);

        licenseEntity = licenseRepository.save(licenseEntity);

        return licenseEntity;
    }

    @Override
    public LicenseEntity findLicenseById(GetLicenseForm form, long licenseId) {
        long userId = form.getUserId();
        LicenseEntity licenseEntity = licenseRepository.findLicenseEntityByUserIdAndLicenseId(userId, licenseId);
        if (licenseEntity == null) {
            throw new EntityNotFoundException();
        }
        return licenseEntity;
    }

    @Override
    public List<Long> getLicenseIds(GetLicenseListForm form) {
        long userId = form.getUserId();
        List<LicenseEntity> list = licenseRepository.getAllByUserId(userId);
        return list.stream().map(LicenseEntity::getLicenseId).collect(Collectors.toList());
    }

    @Override
    public LicenseCheckResponse checkLicense(MultipartFile file) {
        //TODO
        return new LicenseCheckResponse();
    }
}
