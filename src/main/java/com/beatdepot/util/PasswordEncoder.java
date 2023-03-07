package com.beatdepot.util;

import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

public class PasswordEncoder {

    public static String encodePassword(final String password) {
        Map<String, org.springframework.security.crypto.password.PasswordEncoder> encoders = new HashMap<>();
        Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder("", 8, 185000, Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
        encoders.put("pbkdf2", encoder);
        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
        passwordEncoder.setDefaultPasswordEncoderForMatches(encoder);
        String generatedPassword = passwordEncoder.encode(password);

        return generatedPassword.substring(8);
    }
}
