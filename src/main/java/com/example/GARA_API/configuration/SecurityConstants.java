package com.example.GARA_API.configuration;

public class SecurityConstants {
    public static final String SECRET = "...DOTS...";
    public static final long EXPIRATION_TIME = 900_000; //milis
    public static final long REFRESH_TOKEN_EXPIRATION_TIME = 1209600000; // milis
    public static final String TOKEN_PREFIX = "Bearer ";
}
