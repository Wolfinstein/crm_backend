package com.crm.application.utilModels;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

public class CSVMultipartForm {

    @NotNull
    private MultipartFile file;

    public CSVMultipartForm(MultipartFile file) {
        this.file = file;
    }

    public CSVMultipartForm() {
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
