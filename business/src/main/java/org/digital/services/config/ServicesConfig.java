package org.digital.services.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("org.digital.services")
@Import(RabbitMQConfig.class)
public class ServicesConfig {

}
