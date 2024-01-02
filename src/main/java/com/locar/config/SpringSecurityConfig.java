package com.locar.config;

import com.locar.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringSecurityConfig {

    public static final String[] ENDPOINTS_WHITELIST = {
            "/css/**",
            "/",
            "/login",
            "/home",
            "/register"
    };
    public static final String LOGIN_URL = "/login";

    @Autowired
    private UtilisateurService utilisateurService;



}
