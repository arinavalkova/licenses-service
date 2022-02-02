package ru.shift.baldezh.licenses.service.service.impl;

import org.springframework.stereotype.Component;
import ru.shift.baldezh.licenses.service.model.LicenseCheckResponse;
import ru.shift.baldezh.licenses.service.model.forms.license.GetLicenseForm;
import ru.shift.baldezh.licenses.service.model.forms.license.GetLicenseListForm;
import ru.shift.baldezh.licenses.service.model.forms.license.NewLicenseForm;
import ru.shift.baldezh.licenses.service.repository.LicenseRepository;
import ru.shift.baldezh.licenses.service.repository.UserRepository;
import ru.shift.baldezh.licenses.service.repository.model.LicenseEntity;
import ru.shift.baldezh.licenses.service.repository.model.UserInfoEntity;
import ru.shift.baldezh.licenses.service.service.LicenseService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class LicenseServiceImpl implements LicenseService {

    private static final int EXPIRE_TIME_SPAN = 31;
    private final LicenseRepository licenseRepository;

    public LicenseServiceImpl(LicenseRepository licenseRepository) {
        this.licenseRepository = licenseRepository;
    }

    @Override
    public String generateLicense(NewLicenseForm form, OutputStream stream) throws IOException {
        int userId = form.getUserId();

        Date currentDate = new Date();
        LicenseEntity licenseEntity = new LicenseEntity();
        licenseEntity.setCreationDate(currentDate);

        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DAY_OF_MONTH, EXPIRE_TIME_SPAN);
        licenseEntity.setExpirationDate(c.getTime());

        licenseEntity.setSign("sign"); //TODO

        licenseEntity.setMail(form.getMail());

        licenseEntity.setUserId(userId);

        long licenseId = licenseRepository.save(licenseEntity).getLicenseId();

        stream.write(licenseEntity.getCreationDate().toString().getBytes(StandardCharsets.UTF_8));
        stream.write(licenseEntity.getExpirationDate().toString().getBytes(StandardCharsets.UTF_8));
        stream.write(licenseEntity.getSign().getBytes(StandardCharsets.UTF_8));
        stream.write(licenseEntity.getMail().getBytes(StandardCharsets.UTF_8));

        return licenseId + "_license_for_" + userId;
    }

    @Override
    public String findLicenseById(GetLicenseForm form, long licenseId, OutputStream stream) {
        /* TODO: implement */
        return "license";
    }

    @Override
    public List<Long> getLicenseIds(GetLicenseListForm form) {
        return new ArrayList<>();
    }

    @Override
    public LicenseCheckResponse checkLicense(InputStream stream) {
        return new LicenseCheckResponse();
    }
}
