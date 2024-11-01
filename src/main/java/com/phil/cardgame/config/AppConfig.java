package com.phil.cardgame.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = {
        "com.phil.cardgame.aspects",
        "com.phil.cardgame.service"
})
@EnableAspectJAutoProxy()
public class AppConfig {
}
