package org.digital.services.mail_sender_services;

import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import org.digital.task_model.Task;

import java.io.IOException;

public interface MailSender {
    public void sendNewTask(Task task) throws Exception;
}
