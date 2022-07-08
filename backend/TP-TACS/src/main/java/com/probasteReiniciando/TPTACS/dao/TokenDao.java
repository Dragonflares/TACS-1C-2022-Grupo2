package com.probasteReiniciando.TPTACS.dao;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;

@RedisHash("TokenDao")
@Getter
@Setter
@Builder
public class TokenDao implements Serializable {

    @Id
    private String token;

    @TimeToLive
    private Long expiration;

}
