package com.crm.application.service.serviceImpl;


import com.crm.application.repository.ClientRepository;
import com.crm.application.repository.DocumentRepository;
import com.crm.application.utilModels.DocumentMultipartForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.crm.application.model.Document;
import com.crm.application.service.DocumentService;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final ClientRepository clientRepository;


    @Autowired
    public DocumentServiceImpl(DocumentRepository documentRepository, ClientRepository clientRepository) {
        this.documentRepository = documentRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public Optional<Document> getDocumentById(Long id) {
        return Optional.ofNullable(documentRepository.findOneById(id));
    }

    @Override
    public void addDocument(Long id, DocumentMultipartForm form) throws IOException {
        Document document = new Document();
        document.setClient(clientRepository.findOne(id));
        document.setContent(form.getMultipartFile().getBytes());
        document.setExtension(form.getMultipartFile().getContentType());
        document.setName(form.getMultipartFile().getOriginalFilename());
        document.setDescription(form.getDescription());

        documentRepository.save(document);
    }

    @Override
    public void deleteById(Long id) {
        documentRepository.delete(id);
    }


    @Override
    public Collection<Document> getAllDocuments(Long id) {
        return documentRepository.findAllById(id);
    }


}
