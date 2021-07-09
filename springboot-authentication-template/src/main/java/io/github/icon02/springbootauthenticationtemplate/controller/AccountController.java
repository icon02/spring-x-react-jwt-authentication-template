package io.github.icon02.springbootauthenticationtemplate.controller;

import io.github.icon02.springbootauthenticationtemplate.model.User;
import io.github.icon02.springbootauthenticationtemplate.payload.Wrapper;
import io.github.icon02.springbootauthenticationtemplate.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final UserService userService;

    @Autowired
    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Wrapper<String>> get() {
        return ResponseEntity.ok(new Wrapper<>("/account"));
    }

    @PostMapping("/create")
    public ResponseEntity<User> createAccount(@RequestParam String email, @RequestParam String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        userService.createAccount(user);

        return ResponseEntity.ok(user);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class CreateAccountPayload {
        private String email;
        private String password;
    }
}
