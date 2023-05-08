package com.example.GARA_API.exception;

import javax.security.sasl.AuthenticationException;

public class CustomAuthenticationException extends AuthenticationException {
    public CustomAuthenticationException(String detail) {
        super(detail);
    }
}
