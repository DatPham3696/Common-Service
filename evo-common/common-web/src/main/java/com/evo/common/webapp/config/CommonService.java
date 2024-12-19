package com.evo.common.webapp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class CommonService {
    private final RedisService redisService;
    public void storeToken(String tokenKey, String tokenValue, long expiryTimeInSeconds) {
        redisService.saveStringToRedis(tokenKey, tokenValue, expiryTimeInSeconds, TimeUnit.SECONDS);
    }

    public boolean isTokenExist(String tokenKey) {
        String tokenValue = redisService.getStringFromRedis(tokenKey);
        return tokenValue != null;
    }

    public void deleteFromRedis(String key){
        redisService.deleteFromRedis(key);
    }
}
