package ru.shift.baldezh.licenses.library;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.shift.baldezh.licenses.service.model.LicenseCheckResponse;
import ru.shift.baldezh.licenses.service.model.forms.license.CheckLicenseForm;
import ru.shift.baldezh.licenses.service.repository.model.LicenseEntity;
import ru.shift.baldezh.licenses.service.service.LicenseValidator;
import ru.shift.baldezh.licenses.service.service.SignatureDataBuilder;
import ru.shift.baldezh.licenses.service.service.impl.LicenseValidatorImpl;
import ru.shift.baldezh.licenses.service.service.impl.SignatureDataBuilderImpl;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;

public class LicenseChecker {

    private static final String LICENSE_SERVICE_URL = "http://localhost:8080/license/check";
    private static final String SIGNATURE_ALGORITHM = "SHA1WithRSA";
    private final SignatureDataBuilder signatureDataBuilder = new SignatureDataBuilderImpl();
    private final LicenseValidator licenseValidator = new LicenseValidatorImpl();

    public ServerResponseBody isActivated(PublicKey publicKey, LicenseEntity licenseEntity, String uniqueHardwareId)
            throws IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        CheckLicenseForm checkLicenseForm = new CheckLicenseForm();
        checkLicenseForm.setLicense(licenseEntity);
        checkLicenseForm.setUniqueHardwareId(uniqueHardwareId);

        ResponseEntity<String> response = new RestTemplate()
                .postForEntity(LICENSE_SERVICE_URL, checkLicenseForm, String.class);

        if (response.getStatusCode() == HttpStatus.valueOf(418)) {
            return ServerResponseBody.valueOf(response.getBody());
        } else {
            LicenseCheckResponse licenseCheckResponse
                    = new ObjectMapper().readValue(response.getBody(), LicenseCheckResponse.class);
            if (
                    licenseValidator.licenseValid(licenseCheckResponse.getLicense(), licenseEntity)
                &&
                    verify(
                    signatureDataBuilder.getResponseString(licenseCheckResponse.getLicense(), licenseCheckResponse.getUniqueHardwareId()),
                    licenseCheckResponse.getSign(),
                    publicKey
            )) {
                return ServerResponseBody.ACTIVATED;
            } else {
                return ServerResponseBody.BAD_DECODE_LICENSE;
            }
        }
    }

    private boolean verify(String responseString, String sign, PublicKey publicKey)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
        Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);
        sig.initVerify(publicKey);
        sig.update(responseString.getBytes(StandardCharsets.UTF_8));
        return sig.verify(new BASE64Decoder().decodeBuffer(sign));

    }
}
