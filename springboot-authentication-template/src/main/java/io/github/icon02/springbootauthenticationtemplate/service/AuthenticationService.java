package io.github.icon02.springbootauthenticationtemplate.service;

import io.github.icon02.springbootauthenticationtemplate.controller.AuthenticationController;
import io.github.icon02.springbootauthenticationtemplate.exception.SutException;
import io.github.icon02.springbootauthenticationtemplate.exception.WrongPasswordException;
import io.github.icon02.springbootauthenticationtemplate.model.User;
import io.github.icon02.springbootauthenticationtemplate.tempStorage.TemporaryUserStorage;
import io.github.icon02.springbootauthenticationtemplate.util.JwtUtil;
import io.github.icon02.springbootauthenticationtemplate.util.SutUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final JwtUtil jwtUtil;
    private final SutUtil sutUtil;
    private final TemporaryUserStorage userStorage;

    @Autowired
    public AuthenticationService(JwtUtil jwtUtil, SutUtil sutUtil, TemporaryUserStorage userStorage) {
        this.jwtUtil = jwtUtil;
        this.sutUtil = sutUtil;
        this.userStorage = userStorage;
    }

    public AuthenticationController.TokenPayload login(String email, String password, String agentId) {
        User user = userStorage.get(email);
        if(user == null) throw new UsernameNotFoundException("User with email " + email + " not found!");
        if (!user.getPassword().equals(password)) throw new WrongPasswordException(email);

        AuthenticationController.TokenPayload tokenPayload = generate(user);
        String sut = tokenPayload.getSut();

        user.getSingleUseTokens().put(agentId, sut);

        return generate(user);
    }

    public AuthenticationController.TokenPayload renewToken(String email, String sut, String agentId) {
        User user = userStorage.get(email);
        if(user == null) throw new UsernameNotFoundException("User with email " + email + " not found!");
        String actualSut = user.getSingleUseTokens().get(agentId);
        if(actualSut == null || !actualSut.equals(sut)) throw new SutException(email);

        AuthenticationController.TokenPayload tokenPayload = generate(user);
        String newSut = tokenPayload.getSut();
        user.getSingleUseTokens().put(agentId, newSut);

        return tokenPayload;
    }

    private AuthenticationController.TokenPayload generate(User user) {
        String jwt = jwtUtil.generateToken(user);
        String sut = sutUtil.generateToken(user.getEmail());

        AuthenticationController.TokenPayload tokenPayload = new AuthenticationController.TokenPayload();
        tokenPayload.setJwt(jwt);
        tokenPayload.setSut(sut);
        tokenPayload.setJwtValidDuration(JwtUtil.EXPIRATION_DURATION);

        return tokenPayload;
    }
}
