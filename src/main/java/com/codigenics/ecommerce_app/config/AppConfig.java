package com.codigenics.ecommerce_app.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {


    //Config to use model mapper to map the entities and dtos
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
