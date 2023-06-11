package ru.digital.models.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("ru.digital.models")
@EntityScan("ru.digital.models")
public class ModelsConfig {
}
