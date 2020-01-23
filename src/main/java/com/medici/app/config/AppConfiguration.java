package com.medici.app.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan("com.medici.app")
@EnableScheduling
@EnableIntegration
@EnableAutoConfiguration
@IntegrationComponentScan("com.medici.app.integration")
public class AppConfiguration {

}
