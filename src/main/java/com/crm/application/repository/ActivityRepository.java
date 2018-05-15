package com.crm.application.repository;

import com.crm.application.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    <T> Activity findById(Long id);

    @Transactional
    @Query("select a from Activity a where a.user.id = :id")
    List<Activity> findAllByUserId(@Param("id") Long id);

    @Transactional
    @Query("select a from Activity a where a.client.id = :id")
    List<Activity> findAllByClientId(@Param("id") Long id);

}
