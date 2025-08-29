package com.bajaj.service;

import com.bajaj.dto.SolutionRequest;
import com.bajaj.dto.WebhookRequest;
import com.bajaj.dto.WebhookResponse;
import com.bajaj.entity.SqlResult;
import com.bajaj.repository.SqlResultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WebhookService {
    
    private static final Logger logger = LoggerFactory.getLogger(WebhookService.class);
    private static final String GENERATE_WEBHOOK_URL = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";
    private static final String TEST_WEBHOOK_URL = "https://bfhldevapigw.healthrx.co.in/hiring/testWebhook/JAVA";
    
    private final RestTemplate restTemplate;
    private final SqlProblemSolver sqlProblemSolver;
    private final SqlResultRepository sqlResultRepository;
    
    public WebhookService(RestTemplate restTemplate, SqlProblemSolver sqlProblemSolver, SqlResultRepository sqlResultRepository) {
        this.restTemplate = restTemplate;
        this.sqlProblemSolver = sqlProblemSolver;
        this.sqlResultRepository = sqlResultRepository;
    }
    
    public void processWebhookFlow() {
        try {
            logger.info("Starting webhook flow...");
            
            // Step 1: Generate webhook
            WebhookResponse webhookResponse = generateWebhook();
            logger.info("Webhook generated successfully: {}", webhookResponse.getWebhook());
            
            // Step 2: Solve SQL problem
            String regNo = "REG12347"; // From the request body
            String question = sqlProblemSolver.determineQuestion(regNo);
            String solution = sqlProblemSolver.solveSqlProblem(regNo);
            
            logger.info("Question determined: {}", question);
            logger.info("Solution generated: {}", solution);
            
            // Step 3: Store the result
            SqlResult sqlResult = new SqlResult(regNo, question, solution);
            sqlResultRepository.save(sqlResult);
            logger.info("SQL result stored in database");
            
            // Step 4: Submit solution
            submitSolution(webhookResponse.getAccessToken(), solution);
            logger.info("Solution submitted successfully");
            
        } catch (Exception e) {
            logger.error("Error in webhook flow: ", e);
        }
    }
    
    private WebhookResponse generateWebhook() {
        WebhookRequest request = new WebhookRequest("John Doe", "REG12347", "john@example.com");
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<WebhookRequest> entity = new HttpEntity<>(request, headers);
        
        ResponseEntity<WebhookResponse> response = restTemplate.postForEntity(
            GENERATE_WEBHOOK_URL, 
            entity, 
            WebhookResponse.class
        );
        
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to generate webhook. Status: " + response.getStatusCode());
        }
    }
    
    private void submitSolution(String accessToken, String finalQuery) {
        SolutionRequest request = new SolutionRequest(finalQuery);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);
        
        HttpEntity<SolutionRequest> entity = new HttpEntity<>(request, headers);
        
        ResponseEntity<String> response = restTemplate.postForEntity(
            TEST_WEBHOOK_URL,
            entity,
            String.class
        );
        
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Failed to submit solution. Status: " + response.getStatusCode());
        }
    }
} 