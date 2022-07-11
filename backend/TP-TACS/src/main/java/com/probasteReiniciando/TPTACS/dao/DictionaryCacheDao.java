package com.probasteReiniciando.TPTACS.dao;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;

@RedisHash("DictionaryCacheDao")
@Getter
@Setter
@Builder
public class DictionaryCacheDao implements Serializable {


    private String definition;

    @Id
    private String word;


    @TimeToLive
    private Long expiration;

}
