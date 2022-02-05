package ru.shift.baldezh.licenses.service.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.shift.baldezh.licenses.service.model.forms.license.CheckLicenseForm;
import ru.shift.baldezh.licenses.service.model.forms.license.GetLicenseForm;
import ru.shift.baldezh.licenses.service.model.forms.license.GetLicenseListForm;
import ru.shift.baldezh.licenses.service.model.forms.license.NewLicenseForm;
import ru.shift.baldezh.licenses.service.repository.LicenseRepository;
import ru.shift.baldezh.licenses.service.repository.ProductRepository;
import ru.shift.baldezh.licenses.service.repository.model.LicenseEntity;
import ru.shift.baldezh.licenses.service.repository.model.Product;
import ru.shift.baldezh.licenses.service.service.LicenseCryptographyService;
import ru.shift.baldezh.licenses.service.service.LicenseService;
import ru.shift.baldezh.licenses.service.service.LicenseValidator;

import javax.crypto.NoSuchPaddingException;
import javax.persistence.EntityNotFoundException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class LicenseServiceImpl implements LicenseService {

    private static final int EXPIRE_TIME_SPAN = 31;
    private static final int CLOSE_TO_EXPIRE_COUNT_OF_DAYS = 7;

    private final LicenseRepository licenseRepository;
    private final LicenseCryptographyService licenseCryptographyService;
    private final LicenseValidator licenseValidator;
    private final ProductRepository productRepository;


    public LicenseServiceImpl(LicenseRepository licenseRepository,
                              LicenseCryptographyService licenseCryptographyService,
                              LicenseValidator licenseValidator,
                              ProductRepository productRepository) {
        this.licenseRepository = licenseRepository;
        this.licenseCryptographyService = licenseCryptographyService;
        this.licenseValidator = licenseValidator;
        this.productRepository = productRepository;
    }

    @Override
    public LicenseEntity generateLicense(NewLicenseForm form)
            throws NoSuchPaddingException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        LicenseEntity license = null;
        Integer count = form.getCount();
        if (count == null) count = 1;
        for (int i = 0; i < count; i++) {
            license = createLicenseEntity(form);
        }
        return license;
    }

    private LicenseEntity createLicenseEntity(NewLicenseForm form)
            throws NoSuchPaddingException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
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

        licenseEntity.setActivatedHardwareIds(new HashSet<>());

        licenseEntity.setLicenseType(form.getLicenseType());

        Set<Product> setOfProductsWithIds = form
                .getProductIds()
                .stream().map(id -> productRepository.findById(id).get())
                .collect(Collectors.toSet());

        licenseEntity.setProducts(setOfProductsWithIds);

        licenseEntity = licenseRepository.save(licenseEntity);
        licenseEntity = licenseRepository.findById(licenseEntity.getLicenseId()).get();

        return licenseEntity;
    }

    @Override
    public LicenseEntity findLicenseByUserIdAndLicenseId(GetLicenseForm form, long licenseId) {
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
    public ResponseEntity<?> checkLicense(CheckLicenseForm form) {
        try {
            Optional<LicenseEntity> optionalLicenseEntity = licenseRepository.findById(form.getLicense().getLicenseId());

            if (!optionalLicenseEntity.isPresent())
                return ResponseEntity.status(418).body("LICENSE_NOT_EXIST");

            if (!licenseValidator.licenseValid(optionalLicenseEntity.get(), form.getLicense()))
                return ResponseEntity.status(418).body("LICENSE_NOT_EXIST");

            if (licenseExpired(optionalLicenseEntity.get()))
                return ResponseEntity.status(418).body("LICENSE_EXPIRED");

            boolean activationResult = optionalLicenseEntity.get().activate(form.getUniqueHardwareId());
            if (activationResult) {
                licenseRepository.save(optionalLicenseEntity.get());
                return ResponseEntity.ok(licenseCryptographyService.getCheckResponse(form));
            } else
                return ResponseEntity.status(418).body("LICENSE_ALREADY_ACTIVATED");

        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("INTERNAL_SERVER_ERROR");
        }
    }


    private boolean licenseExpired(LicenseEntity license) {
        Date currentDate = new Date();
        Date expirationDate = license.getExpirationDate();
        return currentDate.after(expirationDate);
    }

    @Override
    public List<LicenseEntity> getCloseToExpireLicenses() {
        List<LicenseEntity> closeToExpireLicenses = new ArrayList<>();
        List<LicenseEntity> allLicenses = new ArrayList<>();
        licenseRepository.findAll().forEach(allLicenses::add);

        Date todayDate = new Date();

        Calendar c = Calendar.getInstance();
        c.setTime(todayDate);
        c.add(Calendar.DAY_OF_MONTH, CLOSE_TO_EXPIRE_COUNT_OF_DAYS);
        Date endDate = c.getTime();

        for (LicenseEntity licenseEntity : allLicenses) {
            Date expirationDate = licenseEntity.getExpirationDate();
            if (expirationDate.after(todayDate) && expirationDate.before(endDate)) {
                closeToExpireLicenses.add(licenseEntity);
            }
        }

        return closeToExpireLicenses;
    }
}
