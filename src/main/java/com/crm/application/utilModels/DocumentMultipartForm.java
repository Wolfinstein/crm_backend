package com.crm.application.utilModels;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

public class DocumentMultipartForm {

    @NotNull
    private MultipartFile multipartFile;

    @NotEmpty
    private String description;

    public DocumentMultipartForm() {
    }

    public DocumentMultipartForm(MultipartFile multipartFile, String description) {
        this.multipartFile = multipartFile;
        this.description = description;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
