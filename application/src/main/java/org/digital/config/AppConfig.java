package org.digital.config;

import org.digital.services.config.ServicesConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@Import({ContollersConfig.class,DaoConfig.class,ModelsConfig.class, ServicesConfig.class})
public class AppConfig {
}
