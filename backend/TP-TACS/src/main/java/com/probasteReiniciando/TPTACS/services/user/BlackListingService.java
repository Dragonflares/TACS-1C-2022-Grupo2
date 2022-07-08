package com.probasteReiniciando.TPTACS.services.user;

import com.probasteReiniciando.TPTACS.config.redis.CacheConfig;
import com.probasteReiniciando.TPTACS.dao.TokenDao;
import com.probasteReiniciando.TPTACS.repositories.ITokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class BlackListingService {


    @Autowired
    private ITokenRepository tokenRepository;

    public String blackListJwt(String jwt) {
        tokenRepository.save(TokenDao.builder().token(jwt).expiration(180000L).build());
        return jwt;
    }

    public String getJwtBlackList(String jwt) {
        return tokenRepository.findById(jwt).orElse(TokenDao.builder().build()).getToken();
    }

}