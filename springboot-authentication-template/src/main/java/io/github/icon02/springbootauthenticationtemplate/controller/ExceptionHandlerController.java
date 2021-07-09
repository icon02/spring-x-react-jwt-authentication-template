package io.github.icon02.springbootauthenticationtemplate.controller;

import io.github.icon02.springbootauthenticationtemplate.exception.JwtExpiredException;
import io.github.icon02.springbootauthenticationtemplate.exception.SutException;
import io.github.icon02.springbootauthenticationtemplate.exception.WrongPasswordException;
import io.github.icon02.springbootauthenticationtemplate.payload.CustomErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class ExceptionHandlerController {

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<CustomErrorCode> handleWrongPasswordException() {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new CustomErrorCode(WrongPasswordException.ERROR_CODE));
    }

    @ExceptionHandler(JwtExpiredException.class)
    public ResponseEntity<CustomErrorCode> handleJwtExpiredException() {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new CustomErrorCode(JwtExpiredException.ERROR_CODE));
    }

    @ExceptionHandler(SutException.class)
    public ResponseEntity<CustomErrorCode> handleSutException() {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new CustomErrorCode(SutException.ERROR_CODE));
    }

    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    public ResponseEntity<CustomErrorCode> handleInternalServerError() {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new CustomErrorCode(25));
    }


}
