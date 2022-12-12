package dev.decadev.demo.constant;

public class ApiUriConstant {
    private static final String PATH = "/api/v1/auth";
    public static final String[] AUTH_WHITELIST = {
            PATH +"/signin", PATH + "/forgot-password", PATH + "/reset-password",
            PATH + "/register", PATH + "/verify-code", PATH + "/references",
            "/configuration/**", "/swagger**/**", "/swagger-ui/**", "/webjars/**", "/v3/api-docs/**"
    };
}
