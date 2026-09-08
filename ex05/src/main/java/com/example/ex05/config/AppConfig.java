package com.example.ex05.config;

import com.example.ex05.computer.Computer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public Computer computer(){
        return new Computer();
    }
}
