package com.crm.application.controller;

import com.crm.application.model.User;
import com.crm.application.service.UserService;
import com.crm.application.utilModels.user.AmaEmailForm;
import com.crm.application.utilModels.user.UserCreateForm;
import com.crm.application.validator.UserUpdateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;
    private final UserUpdateValidator userUpdateValidator;

    @Autowired
    public UserController(UserService userService, UserUpdateValidator userUpdateValidator) {
        this.userService = userService;
        this.userUpdateValidator = userUpdateValidator;
    }


    @InitBinder("user")
    public void initUserBinder(WebDataBinder binder) {
        binder.addValidators(userUpdateValidator);
    }


    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody UserCreateForm form) {
        if (userService.getUserByEmail(form.getEmail()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            userService.create(form);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") Long id) {
        if (userService.getUserById(id).isPresent()) {
            userService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable Long id, @Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            userService.update(id, user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/user/show/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUser(@PathVariable("id") Long id) {
        if (!userService.getUserById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userService.getUserById(id).get(), HttpStatus.OK);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity getUsers() {
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
            userService.updatePassword(password, id);
            return new ResponseEntity(HttpStatus.OK);
        }

    }

    @RequestMapping(value = "/user/change-email/{id}", method = RequestMethod.POST)
    public ResponseEntity changeEmail(@PathVariable("id") Long id, @RequestBody String email, BindingResult bindingResult) {
        if (bindingResult.hasErrors() || email.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            userService.updateEmail(email, id);
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/user/ama-email", method = RequestMethod.POST)
    public ResponseEntity sendAmaEmail(@RequestBody AmaEmailForm form, HttpServletRequest request) {
        try {
            if (!userService.getUserByEmail(form.getEmail()).isPresent()) {
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
