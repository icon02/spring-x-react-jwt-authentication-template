package io.github.icon02.springbootauthenticationtemplate.exception;

import io.github.icon02.springbootauthenticationtemplate.model.User;
import lombok.Getter;

@Getter
public class JwtExpiredException extends RuntimeException {

    public static final int ERROR_CODE = 1001;

    private String jwt;
    private User user;

    public JwtExpiredException(String jwt, User user) {
        super("JWT for user with email " + user.getEmail() + " expired!");
        this.jwt = jwt;
        this.user = user;
    }
}
