package ru.digital.business.task_services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.digital.commons.enity_statuses.NotifyStatus;
import ru.digital.commons.enity_statuses.TaskStatus;
import ru.digital.dao.task_dao.TaskRepository;
import ru.digital.models.task_model.Task;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class SchedulerTask {
    private TaskRepository repository;
    private RabbitTemplate rabbitTemplate;
    @Value("${rabbitmq.exchange}")
    private String rabbitExchangeName;
    @Value("${rabbitmq.routingkey}")
    private String rabbitRoutingKey;

    @Autowired
    public SchedulerTask(TaskRepository repository, RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(cron = "${action.notification.cron}")
    public void findOpenTasks() {
        // Every day at 11:00 scheduler will be start
        Date now = new Date();
        log.info("Scheduler was activated!");
        List<Task> openTasks = repository.findBytaskStatusIn(Arrays.asList(
                TaskStatus.INPROGRESS
        ));
        Long countOfNotifies = Long.valueOf(0);
        for (var task : openTasks) {
            if (TimeUnit.HOURS.convert((now.getTime() - task.getDeadLineTime().getTime()),
                    TimeUnit.MILLISECONDS) <= 24) {
                countOfNotifies++;
                rabbitTemplate.convertAndSend(rabbitExchangeName, rabbitRoutingKey,
                        TaskMapper.getNotifyTaskDto(task, NotifyStatus.REMEMBER));
            }
        }
        log.info(countOfNotifies.toString() + " open tasks with soon deadline was found!");
    }
}
