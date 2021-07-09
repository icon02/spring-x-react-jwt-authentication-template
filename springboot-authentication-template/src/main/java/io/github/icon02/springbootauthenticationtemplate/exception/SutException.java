package io.github.icon02.springbootauthenticationtemplate.exception;

import lombok.Getter;

@Getter
public class SutException extends RuntimeException {
    public static final int ERROR_CODE = 1002;

    private String email;

    public SutException(String email) {
        super("User with email " + email + " used invalid sut!");
        this.email = email;
    }
}
