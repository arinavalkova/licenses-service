package ru.shift.baldezh.licenses.service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;

@Configuration
public class CryptoConfiguration {
    @Bean(name = "CRYPTO_ALGORITHM")
    public String getCryptoAlgorithm() {
        return "RSA";
    }
}
