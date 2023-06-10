package org.digital.services.mail_service;


import jakarta.mail.internet.MimeMessage;
import org.digital.employee_model.Employee;
import org.digital.task_model.Task;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.stereotype.Service;

@Service
public class MailSender {
//    @Autowired
//    private JavaMailSender mailSender;
//
//    @RabbitListener(queues = "mails")
//    public void getMessage(Task task){
//        System.out.println(task.getTaskName());
//    }

//    public void send(Employee employee, String subject, String message){
//        MimeMessage mimeMessage = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
//        helper.setSubject("Welcome to QaproTours");
//        helper.setTo(params.get("user.email").toString());
//        String emailContent = getActivationEmailContent(params);
//        helper.setText(emailContent, true);
//        mailSender.send(mimeMessage);
//
//    }
}
