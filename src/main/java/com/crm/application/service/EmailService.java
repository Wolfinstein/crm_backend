package com.crm.application.service;

import com.crm.application.utilModels.Mail;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    void sendEmail(Mail mail, String template);
}
