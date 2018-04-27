package com.healthcare.main.control.service.service;

import com.healthcare.main.entity.model.DetailsPerson;
import com.healthcare.main.util.EmailGood;

public interface EmailService {
    EmailGood getEmail(DetailsPerson person, String subject, String message);
    boolean validateEmail(EmailGood email);
    void sendEmailText(EmailGood email);
    void sendEmailHttp(EmailGood email);
}
