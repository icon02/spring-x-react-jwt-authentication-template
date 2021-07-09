package io.github.icon02.springbootauthenticationtemplate.controller;

import io.github.icon02.springbootauthenticationtemplate.payload.Wrapper;
import io.github.icon02.springbootauthenticationtemplate.service.AuthenticationService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping
    public ResponseEntity<Wrapper<String>> get() {
        return ResponseEntity.ok(new Wrapper<>("/authenticated"));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenPayload> login(@RequestParam String email, @RequestParam String password, @RequestParam String agentId) {
        System.out.println("Touched login method");
        // System.out.println("email: " + email);
        // System.out.println("password: " + password);
        // System.out.println("agentId: " + agentId);
        TokenPayload payload = authenticationService.login(email, password, agentId);

        return ResponseEntity.ok(payload);
    }

    @GetMapping("/jwt")
    public ResponseEntity<TokenPayload> renewJwt(@RequestParam String sut, @RequestParam String email, @RequestParam String agentId) {
        return ResponseEntity.ok(authenticationService.renewToken(email, sut, agentId));
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class LoginPayload {
        private String email;
        private String password;
        private String agentId;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class TokenPayload {
        private String jwt;
        private long jwtValidDuration;
        private String sut;
    }

}
