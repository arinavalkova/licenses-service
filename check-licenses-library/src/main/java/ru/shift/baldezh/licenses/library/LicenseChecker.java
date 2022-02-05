package ru.shift.baldezh.licenses.library;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.shift.baldezh.licenses.service.model.LicenseCheckResponse;
import ru.shift.baldezh.licenses.service.model.forms.license.CheckLicenseForm;
import ru.shift.baldezh.licenses.service.repository.model.LicenseEntity;
import ru.shift.baldezh.licenses.service.repository.model.Product;
import ru.shift.baldezh.licenses.service.service.LicenseValidator;
import ru.shift.baldezh.licenses.service.service.SignatureDataBuilder;
import ru.shift.baldezh.licenses.service.service.impl.LicenseValidatorImpl;
import ru.shift.baldezh.licenses.service.service.impl.SignatureDataBuilderImpl;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;

public class LicenseChecker {

    private static final String LICENSE_SERVICE_URL = "http://localhost:8080/license/check";
    private static final String SIGNATURE_ALGORITHM = "SHA1WithRSA";
    private final SignatureDataBuilder signatureDataBuilder = new SignatureDataBuilderImpl();
    private final LicenseValidator licenseValidator = new LicenseValidatorImpl();

    public ServerResponseBody isActivated(PublicKey publicKey, LicenseEntity licenseEntity, String uniqueHardwareId, Product product)
            throws IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {

        if (!checkIfLicenseCoversCurrentProduct(product, licenseEntity))
            return ServerResponseBody.WRONG_LICENSE_PRODUCT;

        CheckLicenseForm checkLicenseForm = new CheckLicenseForm(licenseEntity, uniqueHardwareId);

        ResponseEntity<String> response = new RestTemplate()
                .postForEntity(LICENSE_SERVICE_URL, checkLicenseForm, String.class);

        if (response.getStatusCode() != HttpStatus.valueOf(200)) {
            return ServerResponseBody.valueOf(response.getBody());
        }

        LicenseCheckResponse licenseCheckResponse =
                new ObjectMapper().readValue(response.getBody(), LicenseCheckResponse.class);

        final boolean responseIsValid = validateResponse(licenseCheckResponse, licenseEntity, publicKey);

        if (responseIsValid) {
            return ServerResponseBody.ACTIVATED;
        } else {
            return ServerResponseBody.BAD_DECODE_LICENSE;
        }
    }

    private boolean checkIfLicenseCoversCurrentProduct(Product product, LicenseEntity license) {
        return license.getProducts().stream().anyMatch(
                p ->
                        p.getProductName().equals(product.getProductName()) &&
                                p.getProductVersion().equals(product.getProductVersion())
        );
    }

    private boolean validateResponse(LicenseCheckResponse licenseCheckResponse, LicenseEntity localLicense, PublicKey publicKey) throws NoSuchAlgorithmException, SignatureException, IOException, InvalidKeyException {
        String signatureData = signatureDataBuilder.getResponseString(
                licenseCheckResponse.getLicense(),
                licenseCheckResponse.getUniqueHardwareId()
        );
        boolean signatureValid = verifySignature(signatureData, licenseCheckResponse.getSign(), publicKey);
        boolean licenseFromServerMatchesLocalLicense = licenseValidator
                .licenseValid(licenseCheckResponse.getLicense(), localLicense);
        return signatureValid && licenseFromServerMatchesLocalLicense;
    }

    private boolean verifySignature(String responseString, String sign, PublicKey publicKey)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
        Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM);
        sig.initVerify(publicKey);
        sig.update(responseString.getBytes(StandardCharsets.UTF_8));
        return sig.verify(new BASE64Decoder().decodeBuffer(sign));
    }
}
