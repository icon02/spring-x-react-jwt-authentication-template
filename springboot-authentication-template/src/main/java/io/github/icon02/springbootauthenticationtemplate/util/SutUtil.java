package io.github.icon02.springbootauthenticationtemplate.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SutUtil {

    public String generateToken(String email) {
        return email + UUID.randomUUID().toString();
    }
}
