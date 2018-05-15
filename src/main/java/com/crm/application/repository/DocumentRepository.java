package com.crm.application.repository;

import com.crm.application.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Transactional
    Document findOneById(Long id);

    @Transactional
    @Query("select a from Document a where a.client.id = :id")
    List<Document> findAllByClientId(@Param("id") Long id);

}
