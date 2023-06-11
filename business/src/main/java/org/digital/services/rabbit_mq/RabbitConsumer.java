package org.digital.services.rabbit_mq;

import lombok.extern.slf4j.Slf4j;
import org.digital.services.mail_sender_services.Impls.MailSenderServiceImpl;
import org.digital.task_model.Task;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@PropertySource("application.properties")
public class RabbitConsumer {
    //Rabbit consumer class for listening queue and send message to mail service
    @Value("${rabbitmq.queue}")
    private String queueName;
    private MailSenderServiceImpl mailSenderService;

    @Autowired
    public RabbitConsumer(MailSenderServiceImpl mailSenderService) {
        this.mailSenderService = mailSenderService;
    }

    @RabbitListener(queues = "my-qu")
    public void receiveMessage(Task task) throws Exception {
        log.info("Getted task with id = " + task.getTaskId() + " from rabbit!");
        mailSenderService.sendNewTask(task);
    }
}
