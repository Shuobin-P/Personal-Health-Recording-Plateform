package com.google.personalhealthrecordingplateform;

import com.google.personalhealthrecordingplateform.util.MD5Utils;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


public class PasswordEncoderTest {

    @Test
    public void test() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String t = passwordEncoder.encode(MD5Utils.md5("fNB5ZklKVa"));
        System.out.println(t); //数据库中的密码
        System.out.println(passwordEncoder.matches(MD5Utils.md5("fNB5ZklKVa"), t));
    }

}
