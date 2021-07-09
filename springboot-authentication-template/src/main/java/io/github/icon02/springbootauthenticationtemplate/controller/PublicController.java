package io.github.icon02.springbootauthenticationtemplate.controller;

import io.github.icon02.springbootauthenticationtemplate.payload.Wrapper;
import io.github.icon02.springbootauthenticationtemplate.util.JwtUtil;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/public")
public class PublicController {

    @GetMapping
    public ResponseEntity<Wrapper<String>> getPublic() {
        return ResponseEntity.ok(new Wrapper<>("Public"));
    }

    @GetMapping("/agentId")
    public ResponseEntity<Wrapper<String>> generateAgentId() {
        String uuid = UUID.randomUUID().toString();
        return ResponseEntity.ok(new Wrapper<>(uuid));
    }

    /**
     * Used in React application to display the timer to
     * indicate how long the jwt will be valid and when
     * the single use token is going to be used to get a new
     * jwt
     * @return
     */
    @GetMapping("/jwtExp")
    public ResponseEntity<Wrapper<Long>> getJwtExpirationDuration() {
        // TODO
        return ResponseEntity.ok(new Wrapper<>(JwtUtil.EXPIRATION_DURATION));
    }



}
