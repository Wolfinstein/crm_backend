package com.crm.application.controller;

import com.crm.application.repository.UserRepository;
import com.crm.application.utilModels.user.AmaEmailForm;
import com.crm.application.utilModels.user.UserCreateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import com.crm.application.model.User;
import com.crm.application.service.UserService;
import com.crm.application.validator.UserUpdateValidator;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;
    private final UserUpdateValidator userUpdateValidator;
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, UserUpdateValidator userUpdateValidator, UserRepository userRepository) {
        this.userService = userService;
        this.userUpdateValidator = userUpdateValidator;

        this.userRepository = userRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @InitBinder("user")
    public void initUserBinder(WebDataBinder binder) {
        binder.addValidators(userUpdateValidator);
    }


    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
    public ResponseEntity<Void> create(@RequestBody UserCreateForm form) {
        if (userService.getUserByEmail(form.getEmail()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            userService.create(form);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> delete(@PathVariable("id") Long id) {
        if (userService.getUserById(id).isPresent()) {
            userService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> update(@PathVariable Long id, @Valid @RequestBody User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            userService.update(id, user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/user/show/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUsers() {

        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/change-password/{id}", method = RequestMethod.POST)
    public ResponseEntity changePassword(@PathVariable("id") Long id, @RequestBody String password, BindingResult bindingResult) {

        if (bindingResult.hasErrors() || password.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            String encodedPassword = passwordEncoder().encode(password.substring(1, password.length() - 1));
            userService.updatePassword(encodedPassword, id);
            return new ResponseEntity(HttpStatus.OK);
        }

    }

    @RequestMapping(value = "/user/change-email/{id}", method = RequestMethod.POST)
    public ResponseEntity changeEmail(@PathVariable("id") Long id, @RequestBody String email, BindingResult bindingResult) {

        if (bindingResult.hasErrors() || email.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            userRepository.updateEmail(email.substring(1, email.length() - 1), id);
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/user/ama-email", method = RequestMethod.POST)
    public ResponseEntity processForgotPasswordForm(@RequestBody AmaEmailForm form, HttpServletRequest request) {
        try {
            if (userService.findUserByEmail(form.getEmail()) == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                userService.amaEmail(form, request);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } catch (HttpMessageNotReadableException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
