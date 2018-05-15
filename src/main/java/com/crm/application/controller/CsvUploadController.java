package com.crm.application.controller;

import com.crm.application.service.ClientService;
import com.crm.application.utilModels.CSVMultipartForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

@RestController
public class CsvUploadController {

    private static final Logger log = LoggerFactory.getLogger(CsvUploadController.class);
    private final JobLauncher jobLauncher;
    private final Job importClientJob;
    private final ClientService clientService;

    @Autowired
    public CsvUploadController(JobLauncher jobLauncher, Job importClientJob, ClientService clientService) {
        this.jobLauncher = jobLauncher;
        this.importClientJob = importClientJob;
        this.clientService = clientService;
    }

    @RequestMapping(value = "/clients/upload/csv", method = RequestMethod.POST)
    public ResponseEntity<Integer> uploadClientsWithCSV(@RequestParam(value = "file", required = false) @Valid MultipartFile file) throws IOException {

        CSVMultipartForm form = new CSVMultipartForm();
        form.setFile(file);
        Integer before = clientService.getAllClients().size();
        fileOperations(form);
        Integer after = clientService.getAllClients().size();

        if (before < after) {
            return new ResponseEntity<>((after - before), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }

    private void fileOperations(CSVMultipartForm file) throws IOException {
        String path = System.getProperty("java.io.tmpdir") + "temp.csv";
        File fileSave = new File(path);
        file.getFile().transferTo(fileSave);

        try {
            JobParametersBuilder builder = new JobParametersBuilder();
            builder.addDate("date", new Date()).addString("fullPathFileName", path);
            jobLauncher.run(importClientJob, builder.toJobParameters());
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            log.error("Error occurred while parsing csv.");
        } finally {
            Files.delete(Paths.get(path));
        }

    }


}
