package ru.digital.business.mail_sender_services;

import ru.digital.dto.task_dto.response_task_dto.NotifyTaskDto;

public interface MailSender {
    public void sendNotificationAboutTask(NotifyTaskDto dto) throws Exception;
}
