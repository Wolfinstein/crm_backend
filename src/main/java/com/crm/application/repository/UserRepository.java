package com.crm.application.repository;

import com.crm.application.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByLogin(String login);

    <T> User findOneByEmail(String email);

    Page<User> findAll(Pageable pageable);

    @Modifying
    @Transactional
    @Query("update User u set u.passwordHash = :passwordHash where u.id = :id")
    void updatePassword(@Param("passwordHash") String passwordHash, @Param("id") Long id);

    @Modifying
    @Transactional
    @Query("update User u set u.email  = :email where u.id = :id")
    void updateEmail(@Param("email") String email, @Param("id") Long id);


}