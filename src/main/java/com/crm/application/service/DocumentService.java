package com.crm.application.service;


import com.crm.application.utilModels.DocumentMultipartForm;
import org.springframework.stereotype.Service;
import com.crm.application.model.Document;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

@Service
public interface DocumentService {

    Optional<Document> getDocumentById(Long id);

    Collection<Document> getAllDocuments(Long id);

    void addDocument(Long id, DocumentMultipartForm form) throws IOException;

    void deleteById(Long id);

}
