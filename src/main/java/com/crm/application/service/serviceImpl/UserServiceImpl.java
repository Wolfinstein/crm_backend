package com.crm.application.service.serviceImpl;


import com.crm.application.model.User;
import com.crm.application.repository.UserRepository;
import com.crm.application.service.EmailService;
import com.crm.application.service.UserService;
import com.crm.application.utilModels.Mail;
import com.crm.application.utilModels.user.AmaEmailForm;
import com.crm.application.utilModels.user.Role;
import com.crm.application.utilModels.user.UserCreateForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final EmailService emailService;
    private final UserRepository userRepository;


    @Autowired
    public UserServiceImpl(EmailService emailService, UserRepository userRepository) {
        this.emailService = emailService;
        this.userRepository = userRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(userRepository.findOneByEmail(email));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return Optional.ofNullable(userRepository.findOne(id));
    }

    @Override
    public User findById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.delete(id);
    }

    @Override
    public User create(UserCreateForm form) {
        User user = new User();
        user.setName(form.getName());
        user.setSurname(form.getSurname());
        user.setLogin(form.getLogin());
        user.setEmail(form.getEmail());
        user.setPasswordHash(new BCryptPasswordEncoder().encode(form.getPassword()));
        user.setCreate_timestamp(Calendar.getInstance().getTime());

        switch (form.getRole()) {
            case "ADMIN": {
                user.setRole(Role.ADMIN);
                break;
            }
            case "MANAGER": {
                user.setRole(Role.MANAGER);
                break;
            }
            case "SALESMAN": {
                user.setRole(Role.SALESMAN);
                break;
            }
        }

        return userRepository.save(user);
    }

    @Override
    public User update(Long id, User user) {
        User temp = userRepository.findOne(id);
        temp.setEmail(user.getEmail());
        temp.setName(user.getName());
        temp.setSurname(user.getSurname());
        temp.setRole(user.getRole());
        return userRepository.save(temp);
    }

    @Override
    public void updatePassword(String password, Long userId) {
        String encodedPassword = passwordEncoder().encode(password.substring(1, password.length() - 1));
        userRepository.updatePassword(encodedPassword, userId);
    }

    public String getLoggedInUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.error("name " + auth.getName() + " auths " + auth.getAuthorities().toString() + " credentials " + auth.getCredentials() + " isAuth " + auth.isAuthenticated() + " principal " + auth.getPrincipal().toString());

        return auth.getName();
    }

    @Override
    public void amaEmail(AmaEmailForm form, HttpServletRequest request) {
        Mail mail = new Mail();
        mail.setFrom("ama-no-reply@crm-application.com");
        mail.setTo("springrecoverytoken@gmail.com");
        mail.setSubject("Question from " + form.getName());
        Map<String, Object> model = new HashMap<>();
        model.put("signature", "Crm-application.com");
        mail.setModel(model);
        model.put("form", form);
        emailService.sendEmail(mail, "ama-template");
    }

    @Override
    public void updateEmail(String email, Long userId) {
        userRepository.updateEmail(email.substring(1, email.length() - 1), userId);
    }


}