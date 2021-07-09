package io.github.icon02.springbootauthenticationtemplate.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/triggerArrayIndexOutOfBounds")
    public ResponseEntity<String> triggerArrayIndexOutOfBounds() {
        String[] arr = new String[10];
        String unreachableVal = arr[11];

        return ResponseEntity.ok(unreachableVal);
    }
}
