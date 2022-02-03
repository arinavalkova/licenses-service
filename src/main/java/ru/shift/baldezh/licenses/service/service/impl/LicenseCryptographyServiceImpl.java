package ru.shift.baldezh.licenses.service.service.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.shift.baldezh.licenses.service.model.LicenseCheckResponse;
import ru.shift.baldezh.licenses.service.model.forms.license.CheckLicenseForm;
import ru.shift.baldezh.licenses.service.repository.model.LicenseEntity;
import ru.shift.baldezh.licenses.service.service.LicenseCryptographyService;
import ru.shift.baldezh.licenses.service.service.PrivateKeyProvider;
import sun.misc.BASE64Encoder;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;

@Component
public class LicenseCryptographyServiceImpl implements LicenseCryptographyService {
    private static final String SEPARATOR = ";";
    private final PrivateKeyProvider privateKeyProvider;
    private final String cryptoAlgorithm;
    private final String signatureAlgorithm;

    public LicenseCryptographyServiceImpl(
            PrivateKeyProvider privateKeyProvider,
            @Qualifier("CRYPTO_ALGORITHM") String cryptoAlgorithm,
            @Qualifier("SIGNATURE_ALGORITHM") String signatureAlgorithm
    ) {
        this.cryptoAlgorithm = cryptoAlgorithm;
        this.privateKeyProvider = privateKeyProvider;
        this.signatureAlgorithm = signatureAlgorithm;
    }

    @Override
    public void sign(LicenseEntity entity)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        entity.setSign(
                getSignature(
                        getLicenseString(entity)));
    }

    @Override
    public LicenseCheckResponse getCheckResponse(CheckLicenseForm form)
            throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {

        String data = getLicenseString(form.getLicense()) + SEPARATOR + form.getUniqueHardwareId();

        return new LicenseCheckResponse(
                form.getLicense(), form.getUniqueHardwareId(),
                getSignature(data)
        );
    }

    private String getLicenseString(LicenseEntity entity) {
        return entity.getLicenseId() + SEPARATOR +
                entity.getMail() + SEPARATOR +
                entity.getCreationDate().toString() + SEPARATOR +
                entity.getExpirationDate().toString();
    }

    private String getSignature(String data)
            throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
        Signature sig = Signature.getInstance(signatureAlgorithm);
        sig.initSign(privateKeyProvider.getPrivateKey());
        sig.update(data.getBytes(StandardCharsets.UTF_8));
        return new BASE64Encoder().encode(sig.sign());
    }
}
