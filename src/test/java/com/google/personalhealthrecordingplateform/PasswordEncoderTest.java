package com.google.personalhealthrecordingplateform;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


public class PasswordEncoderTest {

    @Test
    public void test() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("123456"));
        System.out.println(passwordEncoder.matches("e10adc3949ba59abbe56e057f20f883e", "$2a$10$H4TnTlyPD6R/hvMj0sEzseqguWzt.GzI/TtJPLgXPdEDQRbUfPDde"));
    }

}
