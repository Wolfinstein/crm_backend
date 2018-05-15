package com.crm.application.controller;

import com.crm.application.service.PasswordResetTokenService;
import com.crm.application.utilModels.user.PasswordReset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/reset-password")
public class PasswordResetController {

    private final PasswordResetTokenService passwordResetTokenService;

    @Autowired
    public PasswordResetController(PasswordResetTokenService passwordResetTokenService) {
        this.passwordResetTokenService = passwordResetTokenService;
    }

    @PostMapping
    public ResponseEntity<Void> handlePasswordReset(@RequestBody @Valid PasswordReset form,
                                                    BindingResult result) {
        if (form == null || !passwordResetTokenService.findByToken(form.getToken()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        passwordResetTokenService.updatePasswordAndDeleteToken(form);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}