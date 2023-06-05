package org.digital.config;

import unit.org.digital.services.config.ServicesConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ContollersConfig.class,DaoConfig.class,ModelsConfig.class, ServicesConfig.class})
public class AppConfig {
}
