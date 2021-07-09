package io.github.icon02.springbootauthenticationtemplate.exception;

import lombok.Getter;

@Getter
public class WrongPasswordException extends RuntimeException {

    public static final int ERROR_CODE = 1000;

    private String email;

    public WrongPasswordException(String email) {
        super("User with email " + email + " entered wrong password!");
        this.email = email;
    }
}
