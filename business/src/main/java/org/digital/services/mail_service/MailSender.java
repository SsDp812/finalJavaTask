package org.digital.services.mail_service;


import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.digital.task_model.Task;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailSender {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Configuration freeMakerConfig;

    @RabbitListener(queues = "my")
    public void getMessage(Task task) throws MessagingException, TemplateException, IOException {
        System.out.println("getted from mq");
        sendNewTask(task);
    }


    private void sendNewTask(Task task) throws MessagingException, TemplateException, IOException {
        System.out.println("catch!");
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("New task!");
        helper.setTo(task.getExecutor().getEmail());
        String emailContent = getContent(task);
        System.out.println("next");
        helper.setText(emailContent, true);
        System.out.println("Mail sended!");
        try {
            mailSender.send(mimeMessage);
            helper.setTo("efan.daniel2002@gmail.com");
            mailSender.send(mimeMessage);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private String getContent(Task task) throws IOException, TemplateException {
        System.out.println("aga aga");
        StringWriter stringWriter = new StringWriter();

        Map<String, Object> model = new HashMap<>();
        model.put("name", task.getExecutor().getName() + " " + task.getExecutor().getSurname());
        model.put("taskname", task.getTaskName());
        model.put("taskdescription", task.getTaskDescription());
        model.put("taskdeadline", task.getDeadLineTime().toString());
        System.out.println("aga2 aga");
        try {
            freeMakerConfig.getTemplate("taskMail.ftl")
                    .process(model, stringWriter);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println("aga3 aga");
        return stringWriter.getBuffer()
                .toString();
    }
}
