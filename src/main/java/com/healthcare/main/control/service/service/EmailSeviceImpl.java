package com.healthcare.main.control.service.service;

import com.healthcare.main.entity.model.DetailsPerson;
import com.healthcare.main.util.EmailGood;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EmailSeviceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    @Qualifier("freeMarkerConfigBean")
    private Configuration freemarkerConfig;

    // used to validate emails
    private static final Pattern REGEX_EMAIL_EXAMPLE_PATTERN = Pattern.compile("(@example)");

    @Override
    public EmailGood getEmail(DetailsPerson person, String subject, String message) {
        EmailGood email = new EmailGood();
        email.setFrom("test.demo.fii.practic.spring.2018@gmail.com");
        email.setTo(person.getEmail().getEmail());
        email.setSubject(subject);

        Map<String, String> content = new HashMap<>();
        content.put("name", person.getFirstName() + " " + person.getLastName());
        content.put("message", message);
        email.setContent(content);

        return email;
    }

    @Override
    public boolean validateEmail(EmailGood email) {
        Matcher m = REGEX_EMAIL_EXAMPLE_PATTERN.matcher(email.getTo());
        if(m.find())
        {
            return false;
        }
        return true;
    }

    @Override
    public void sendEmailText(EmailGood email) {

        if(validateEmail(email)) {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

            try {
                mimeMessageHelper.setTo(email.getTo());
                mimeMessageHelper.setSubject(email.getSubject());
                mimeMessageHelper.setText((String) email.getContent().get("message"));
                javaMailSender.send(mimeMessage);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void sendEmailHttp(EmailGood email) {

        if(validateEmail(email)) {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

            try
            {
                Template t = freemarkerConfig.getTemplate("basic_freemarker_email.ftl");
                String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, email.getContent());

                mimeMessageHelper.setFrom(email.getFrom());
                mimeMessageHelper.setTo(email.getTo());
                mimeMessageHelper.setSubject(email.getSubject());
                mimeMessageHelper.setText(html, true);
                javaMailSender.send(mimeMessage);
            }
            catch (TemplateNotFoundException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            catch (MessagingException e) {
                e.printStackTrace();
            }
            catch (TemplateException e) {
                e.printStackTrace();
            }
        }
    }
}
