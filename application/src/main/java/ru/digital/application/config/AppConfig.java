package ru.digital.application.config;

import org.springframework.context.annotation.PropertySource;
import ru.digital.controllers.config.ContollersConfig;
import ru.digital.dao.config.DaoConfig;
import ru.digital.models.config.ModelsConfig;
import ru.digital.business.config.ServicesConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ContollersConfig.class, DaoConfig.class, ModelsConfig.class,
        ServicesConfig.class})
public class AppConfig {

}
