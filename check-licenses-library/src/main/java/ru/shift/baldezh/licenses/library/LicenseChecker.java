package ru.shift.baldezh.licenses.library;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.shift.baldezh.licenses.service.model.LicenseCheckResponse;
import ru.shift.baldezh.licenses.service.model.forms.license.CheckLicenseForm;
import ru.shift.baldezh.licenses.service.repository.model.LicenseEntity;
import sun.misc.BASE64Encoder;

import java.nio.charset.StandardCharsets;
import java.security.*;

public class LicenseChecker {

    private static final String LICENSE_SERVICE_URL = "http://localhost:8080/license/check";
    private static final String SIGNATURE_ALGORITHM = "SHA1WithRSA";

    public ServerResponseBody isActivated(PublicKey publicKey, LicenseEntity licenseEntity, String uniqueHardwareId)
            throws JsonProcessingException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
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
            String sign = licenseCheckResponse.getSign();
            String decipherSign = decipher(sign, publicKey);
            if (isDecipherSignValid(decipherSign, licenseEntity, uniqueHardwareId)) {
                return ServerResponseBody.ACTIVATED;
            } else {
                return ServerResponseBody.BAD_DECODE_LICENSE;
            }
        }
    }

    private boolean isDecipherSignValid(String decipherSign, LicenseEntity licenseEntity, String uniqueHardwareId)
            throws JsonProcessingException {
        CheckLicenseForm checkLicenseFormFromSign = new ObjectMapper().readValue(decipherSign, CheckLicenseForm.class);
        if (!checkLicenseFormFromSign.getUniqueHardwareId().equals(uniqueHardwareId)) {
            return false;
        }
        LicenseEntity licenseFromSign = checkLicenseFormFromSign.getLicense();

        if (!licenseFromSign.getExpirationDate().equals(licenseEntity.getExpirationDate())) return false;
        if (!licenseFromSign.getCreationDate().equals(licenseEntity.getCreationDate())) return false;
        if (!licenseFromSign.getSign().equals(licenseEntity.getSign())) return false;
        if (!licenseFromSign.getMail().equals(licenseEntity.getMail())) return false;
        if (licenseFromSign.getUserId() != licenseEntity.getUserId()) return false;
        if (licenseFromSign.getActivationCount() != licenseEntity.getActivationCount()) return false;
        if (!licenseFromSign.getActivatedUniqueHardwareId().equals(licenseEntity.getActivatedUniqueHardwareId()))
            return false;
        return true;
    }

    private String decipher(String data, PublicKey publicKey)
            throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
        Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);
        sig.initVerify(publicKey);
        sig.update(data.getBytes(StandardCharsets.UTF_8));
        return new BASE64Encoder().encode(sig.sign());
    }
}
