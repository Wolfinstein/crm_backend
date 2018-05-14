package com.crm.application.service.serviceImpl;

import com.crm.application.repository.PasswordResetTokenRepository;
import com.crm.application.utilModels.Mail;
import com.crm.application.utilModels.user.PasswordForgot;
import com.crm.application.utilModels.user.PasswordReset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.crm.application.model.PasswordResetToken;
import com.crm.application.model.User;
import com.crm.application.service.EmailService;
import com.crm.application.service.PasswordResetTokenService;
import com.crm.application.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private static final Logger log = LoggerFactory.getLogger(PasswordResetTokenServiceImpl.class);
    private final UserService userService;
    private final PasswordResetTokenRepository tokenRepository;
    private final EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordResetTokenServiceImpl(PasswordResetTokenRepository tokenRepository, UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
        this.tokenRepository = tokenRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void generateTokenAndSendMail(PasswordForgot form, HttpServletRequest request) {

        User user = userService.findUserByEmail(form.getEmail());

        int port = 4200;

        PasswordResetToken token = new PasswordResetToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setExpiryDate(30);
        tokenRepository.save(token);

        Mail mail = new Mail();
        mail.setFrom("no-reply@billennium.com");
        mail.setTo(user.getEmail());
        mail.setSubject("Password reset request");

        Map<String, Object> model = new HashMap<>();
        model.put("token", token);
        model.put("user", user);
        model.put("signature", "Billennium.com");
        String url = request.getScheme() + "://" + request.getServerName() + ":" + port; // request.getServerPort();
        model.put("resetUrl", url + "/reset-password?token=" + token.getToken());
        mail.setModel(model);
        emailService.sendEmail(mail, "password-forgot-template");
    }

    @Override
    public void updatePasswordAndDeleteToken(PasswordReset form) {

        try {
            PasswordResetToken token = tokenRepository.findByToken(form.getToken()).get();
            User user = token.getUser();
            String updatedPassword = passwordEncoder.encode(form.getPassword());
            userService.updatePassword(updatedPassword, user.getId());
            tokenRepository.delete(token);
        } catch (NoSuchElementException ex) {
            log.debug("Reset Password: " + ex.getMessage());
        }
    }
}
