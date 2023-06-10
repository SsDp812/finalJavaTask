package org.digital.config;

import org.digital.services.config.ServicesConfig;
import org.digital.services.config.mail.MailConfig;
import org.digital.services.config.rabbit.RabbitMQConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ContollersConfig.class,DaoConfig.class,ModelsConfig.class, ServicesConfig.class})
public class AppConfig {
}
