package com.google.personalhealthrecordingplateform.configs.security.content;

public class SecurityContent {
    public static String[] WHITE_LIST = {
            "/user/login",
            "/webjars/**",
            "/swagger-ui.html",
            "/doc.html",
            "swagger-resources/**",
            "v2/**",
            "configuration/ui",
            "configuration/security"
    };
}
