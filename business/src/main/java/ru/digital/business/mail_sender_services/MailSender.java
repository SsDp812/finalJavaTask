package ru.digital.business.mail_sender_services;

import ru.digital.models.task_model.Task;

public interface MailSender {
    public void sendNotificationAboutTask(Task task) throws Exception;
}
