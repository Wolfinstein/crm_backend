package com.crm.application.service;

import com.crm.application.model.PasswordResetToken;
import com.crm.application.utilModels.user.PasswordReset;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public interface PasswordResetTokenService {

    void generateTokenAndSendMail(String email, HttpServletRequest request);

    void updatePasswordAndDeleteToken(PasswordReset passwordReset);

    Optional<PasswordResetToken> findByToken(String token);


}
