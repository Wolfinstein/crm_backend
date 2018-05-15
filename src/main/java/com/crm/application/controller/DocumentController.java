package com.crm.application.controller;

import com.crm.application.model.Document;
import com.crm.application.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @RequestMapping(value = "documents/{id}", method = RequestMethod.POST)
    public ResponseEntity addDocument(@PathVariable("id") Long id, @RequestParam(value = "file", required = false) @Valid MultipartFile file, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        } else {
            documentService.addDocument(id, file);
            return new ResponseEntity(HttpStatus.OK);
        }
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
        if (documentService.getDocumentById(id).isPresent()) {
            Document document = documentService.getDocumentById(id).get();

            return ResponseEntity
                    .ok()
                    .contentType(MediaType.parseMediaType(document.getExtension()))
                    .body(new ByteArrayResource(document.getContent()));
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        }
    }

    @RequestMapping(value = "documents/client/{id}", method = RequestMethod.GET)
    public ResponseEntity getDocumentsByClientId(@PathVariable("id") Long id) {
        List<Document> documents = documentService.getAllDocumentsByClientId(id);
        if (documents.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(documents, HttpStatus.OK);
        }
    }

}

