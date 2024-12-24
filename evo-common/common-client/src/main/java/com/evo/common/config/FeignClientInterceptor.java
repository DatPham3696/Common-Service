package com.evo.common.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Data
public class FeignClientInterceptor implements RequestInterceptor {
    @Value("${spring.storage.client-id}")
    private String client_id;
    @Value("${spring.storage.client-secret}")
    private String client_secret;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String token = getClientToken();
        if(token != null && !token.isEmpty()){
            requestTemplate.header("Authorization", "Bearer " + token);
        }
    }

    private String getClientToken(){
        String tokenUri = "http://localhost:8090/iam/api/users/verify-client-key/{clientId}/{clientSecret}";
        String clientId = client_id;
        String clientSecret = client_secret;
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(
                    tokenUri,
                    String.class,
                    clientId,
                    clientSecret
            );
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                throw new RuntimeException("Failed to retrieve token. Status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while fetching client token: " + e.getMessage(), e);
        }
    }
}
