package ru.shift.baldezh.licenses.service.service.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
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
    private final PrivateKeyProvider privateKeyProvider;
    private final String cryptoAlgorithm;

    public LicenseCryptographyServiceImpl(
            PrivateKeyProvider privateKeyProvider,
            @Qualifier("CRYPTO_ALGORITHM") String cryptoAlgorithm
    ) {
        this.cryptoAlgorithm = cryptoAlgorithm;
        this.privateKeyProvider = privateKeyProvider;
    }

    @Override
    public void sign(LicenseEntity entity) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        String data = entity.getLicenseId() + ";" +
                entity.getMail() + ";" +
                entity.getCreationDate().toString() + ";" +
                entity.getExpirationDate().toString();
        entity.setSign(
                getSignature(data)
        );
    }

    @Override
    public LicenseCheckResponse getCheckResponse(CheckLicenseForm form) {

        return new LicenseCheckResponse();
    }

    private String getSignature(String data) throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
        Signature sig = Signature.getInstance("SHA1WithRSA");
        sig.initSign(privateKeyProvider.getPrivateKey());
        sig.update(data.getBytes(StandardCharsets.UTF_8));
        return new BASE64Encoder().encode(sig.sign());
    }
}
