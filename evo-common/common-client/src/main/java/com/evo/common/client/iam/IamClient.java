package com.evo.common.client.iam;

import com.evo.common.UserAuthority;
import com.evo.common.config.FeignClientConfiguration;
import com.evo.common.dto.response.Response;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(
        name = "${spring.application.name}",
        url = "http://localhost:8080",
        configuration = FeignClientConfiguration.class
)
public interface IamClient {
    Response<UserAuthority> getUserAuthority(UUID userId);

    Response<UserAuthority> getUserAuthority(String username);

    @GetMapping("/api/users/verify-client-key")
    Response<?> generateToken(@RequestParam("clientId") String clientId,
                              @RequestParam("clientSecret") String clientSecret);

}
