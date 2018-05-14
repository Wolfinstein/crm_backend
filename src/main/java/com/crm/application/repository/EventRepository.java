package com.crm.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.crm.application.model.Event;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    <T> Event findById(Long id);

    @Transactional
    @Query("select a from Activity a where a.user.id = :id")
    List<Event> findAllByUserId(@Param("id") Long id);
}
