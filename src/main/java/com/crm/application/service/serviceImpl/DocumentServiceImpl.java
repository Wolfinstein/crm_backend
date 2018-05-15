package com.crm.application.service.serviceImpl;


import com.crm.application.model.Document;
import com.crm.application.repository.ClientRepository;
import com.crm.application.repository.DocumentRepository;
import com.crm.application.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
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
    public List<Document> getAllDocumentsByClientId(Long id) {
        return documentRepository.findAllByClientId(id);
    }

    @Override
    public void addDocument(Long id, MultipartFile file) throws IOException {
        Document document = new Document();
        document.setClient(clientRepository.findOne(id));
        document.setContent(file.getBytes());
        document.setExtension(file.getContentType());
        document.setName(file.getOriginalFilename());

        documentRepository.save(document);
    }

    @Override
    public void deleteById(Long id) {
        documentRepository.delete(id);
    }


}
