package com.ecureuill.rpgbattle.infrastructure.openAPI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SpringDocConfig {
  @Bean
  public OpenAPI customOpenAPI(){
    return new OpenAPI()
      .info(new Info()
        .title("RPG Battle API")
        .description("API Rest for a RPG Battle game")
        .version("0.0.1")
        .contact(new Contact()
          .name("Camilla Silva")
          .email("logikasciuro@gmail.com")
          .url("http://github.com/ecureuill")));

  }
}
