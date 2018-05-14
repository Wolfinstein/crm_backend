package com.crm.application.controller;

import com.crm.application.repository.DocumentRepository;
import com.crm.application.utilModels.DocumentMultipartForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.crm.application.model.Document;
import com.crm.application.service.DocumentService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
public class DocumentController {

    private final DocumentService documentService;
    private final DocumentRepository documentRepository;

    @Autowired
    public DocumentController(DocumentService documentService, DocumentRepository documentRepository) {
        this.documentService = documentService;
        this.documentRepository = documentRepository;
    }

    @RequestMapping(value = "documents/{id}", method = RequestMethod.POST)
    public ResponseEntity addDocument(@PathVariable("id") Long id, @RequestParam(value = "file", required = false) @Valid MultipartFile file) throws IOException {

        DocumentMultipartForm form = new DocumentMultipartForm();
        form.setMultipartFile(file);
        form.setDescription("whatever");

        documentService.addDocument(id, form);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/documents/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteDocument(@PathVariable("id") Long id) {
        if (documentService.getDocumentById(id).isPresent()) {
            documentService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/documents/{id}", method = RequestMethod.GET)
    public ResponseEntity<ByteArrayResource> downloadDocumentFile(@PathVariable Long id) {
        Document file = documentRepository.findOneById(id);
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(file.getExtension()))
                .body(new ByteArrayResource(file.getContent()));
    }

    @RequestMapping(value = "documents/client/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Document>> getDocumentsByClientId(@PathVariable("id") Long id) {
        List<Document> documents = documentRepository.findAllById(id);

        if (documents.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(documents, HttpStatus.OK);
        }
    }

}

