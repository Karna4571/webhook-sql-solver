package com.bajaj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import com.bajaj.service.WebhookService;

@SpringBootApplication
public class WebhookSqlSolverApplication {

    private final WebhookService webhookService;

    public WebhookSqlSolverApplication(WebhookService webhookService) {
        this.webhookService = webhookService;
    }

    public static void main(String[] args) {
        SpringApplication.run(WebhookSqlSolverApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        webhookService.processWebhookFlow();
    }
} 