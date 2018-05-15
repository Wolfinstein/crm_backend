package com.crm.application.service;


import com.crm.application.model.Document;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public interface DocumentService {

    Optional<Document> getDocumentById(Long id);

    List<Document> getAllDocumentsByClientId(Long id);

    void addDocument(Long id, MultipartFile file) throws IOException;

    void deleteById(Long id);

}
