package br.com.mateusulrich.recipeservice.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.util.Json;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return Json.mapper();
    }
}
