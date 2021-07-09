package io.github.icon02.springbootauthenticationtemplate.controller;

import io.github.icon02.springbootauthenticationtemplate.payload.Wrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public ResponseEntity<Wrapper<String>> get() {
        return ResponseEntity.ok(new Wrapper<>("User is admin"));
    }
}
