package com.crm.application.controller;

import com.crm.application.utilModels.user.PasswordForgot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.crm.application.service.PasswordResetTokenService;
import com.crm.application.service.UserService;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/forgot-password")
public class PasswordForgotController {

    private final UserService userService;
    private final PasswordResetTokenService passwordResetTokenService;

    @Autowired
    public PasswordForgotController(UserService userService, PasswordResetTokenService passwordResetTokenService) {
        this.userService = userService;
        this.passwordResetTokenService = passwordResetTokenService;
    }

    @PostMapping
    public ResponseEntity processForgotPasswordForm(@RequestBody PasswordForgot form, HttpServletRequest request) {

        try {
            if (userService.findUserByEmail(form.getEmail()) == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                passwordResetTokenService.generateTokenAndSendMail(form, request);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } catch (HttpMessageNotReadableException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}