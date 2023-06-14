package ru.digital.business.rabbit_mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.digital.business.mail_sender_services.MailSender;
import ru.digital.dto.task_dto.response_task_dto.NotifyTaskDto;


@Component
@Slf4j
public class RabbitConsumer {
    //Rabbit consumer class for listening queue and send message to mail service
    private MailSender mailSenderService;

    @Autowired
    public RabbitConsumer(MailSender mailSenderService) {
        this.mailSenderService = mailSenderService;
    }

    @RabbitListener(queues = "myQueue")
    public void receiveMessage(NotifyTaskDto dto) throws Exception {
        log.info("Getted task with id = " + dto.getTaskId() + " from rabbit!");
        mailSenderService.sendNotificationAboutTask(dto);
    }
}
