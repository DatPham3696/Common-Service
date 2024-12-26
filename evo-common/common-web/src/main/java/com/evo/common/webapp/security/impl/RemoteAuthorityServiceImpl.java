package com.evo.common.webapp.security.impl;

import com.evo.common.UserAuthority;
import com.evo.common.client.iam.IamClient;
import com.evo.common.webapp.security.AuthorityService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.dialect.pagination.LegacyOracleLimitHandler;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class RemoteAuthorityServiceImpl implements AuthorityService {
    private final IamClient iamClient;

    public RemoteAuthorityServiceImpl(IamClient iamClient) {
        this.iamClient = iamClient;
    }

    @Override
    public UserAuthority getUserAuthority(String email) {
//        log.info(iamClient.getUserAuthority(email).getData().toString());
        return iamClient.getUserAuthority(email).getBody();
    }

    @Override
    public UserAuthority getClientAuthority(UUID clientId) {
        return null;
    }
}
