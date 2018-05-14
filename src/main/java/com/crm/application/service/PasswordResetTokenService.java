package com.crm.application.service;

import com.crm.application.utilModels.user.PasswordForgot;
import com.crm.application.utilModels.user.PasswordReset;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface PasswordResetTokenService {

    void generateTokenAndSendMail(PasswordForgot form, HttpServletRequest request);

    void updatePasswordAndDeleteToken(PasswordReset passwordReset);


}
