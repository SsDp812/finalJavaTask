package ru.digital.business.mail_sender_services;

import ru.digital.models.task_model.Task;

public interface MailSender {
    public void sendNewTask(Task task) throws Exception;
}
