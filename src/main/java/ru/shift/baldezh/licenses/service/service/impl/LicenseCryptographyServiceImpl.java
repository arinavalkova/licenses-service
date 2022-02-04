package ru.shift.baldezh.licenses.service.service.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.shift.baldezh.licenses.service.model.LicenseCheckResponse;
import ru.shift.baldezh.licenses.service.model.forms.license.CheckLicenseForm;
import ru.shift.baldezh.licenses.service.repository.model.LicenseEntity;
import ru.shift.baldezh.licenses.service.service.LicenseCryptographyService;
import ru.shift.baldezh.licenses.service.service.PrivateKeyProvider;
import ru.shift.baldezh.licenses.service.service.SignatureDataBuilder;
import sun.misc.BASE64Encoder;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;

@Component
public class LicenseCryptographyServiceImpl implements LicenseCryptographyService {
    private final PrivateKeyProvider privateKeyProvider;
    private final String cryptoAlgorithm;
    private final String signatureAlgorithm;
    private final SignatureDataBuilder signatureDataBuilder;

    public LicenseCryptographyServiceImpl(
            PrivateKeyProvider privateKeyProvider,
            @Qualifier("CRYPTO_ALGORITHM") String cryptoAlgorithm,
            @Qualifier("SIGNATURE_ALGORITHM") String signatureAlgorithm,
            SignatureDataBuilder signatureDataBuilder) {
        this.cryptoAlgorithm = cryptoAlgorithm;
        this.privateKeyProvider = privateKeyProvider;
        this.signatureAlgorithm = signatureAlgorithm;
        this.signatureDataBuilder = signatureDataBuilder;
    }

    @Override
    public void sign(LicenseEntity entity)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        entity.setSign(
                getSignature(
                        signatureDataBuilder.getLicenseString(entity)));
    }

    @Override
    public LicenseCheckResponse getCheckResponse(CheckLicenseForm form)
            throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {

        String data = signatureDataBuilder.getResponseString(form.getLicense(), form.getUniqueHardwareId());

        return new LicenseCheckResponse(
                form.getLicense(), form.getUniqueHardwareId(),
                getSignature(data)
        );
    }

    private String getSignature(String data)
            throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
        Signature sig = Signature.getInstance(signatureAlgorithm);
        sig.initSign(privateKeyProvider.getPrivateKey());
        sig.update(data.getBytes(StandardCharsets.UTF_8));
        return new BASE64Encoder().encode(sig.sign());
    }
}
