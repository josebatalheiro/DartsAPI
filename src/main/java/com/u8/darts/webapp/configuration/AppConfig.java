package com.u8.darts.webapp.configuration;

import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfig {
    public String getDartsPath() {
        //TODO proper config file with yaml
        return "src/python/darts";
    }

}
