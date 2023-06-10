package org.digital.config;

import org.digital.services.config.ServicesConfig;
<<<<<<< HEAD
import org.digital.services.config.mail.MailConfig;
import org.digital.services.config.rabbit.RabbitMQConfig;
=======
>>>>>>> parent of bb967b6 (rabbit)
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ContollersConfig.class,DaoConfig.class,ModelsConfig.class, ServicesConfig.class})
public class AppConfig {
}
