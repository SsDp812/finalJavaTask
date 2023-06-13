package ru.digital.business.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("ru.digital.business")
@Import({RabbitMQConfig.class, SchedulerConfig.class})
public class ServicesConfig {

}
