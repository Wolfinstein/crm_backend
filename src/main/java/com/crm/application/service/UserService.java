package com.crm.application.service;

import com.crm.application.model.User;
import com.crm.application.utilModels.user.AmaEmailForm;
import com.crm.application.utilModels.user.UserCreateForm;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    Optional<User> getUserById(Long id);

    User findById(Long id);

    Optional<User> getUserByEmail(String email);

    List<User> getAllUsers();

    User create(UserCreateForm form);

    void deleteById(Long id);

    User update(Long id, User user);

    void updatePassword(String password, Long id);

    String getLoggedInUserId();

    void amaEmail(AmaEmailForm form, HttpServletRequest request);

    void updateEmail(String email, Long userId);
}
