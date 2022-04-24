package com.probasteReiniciando.TPTACS.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class User {
    
    private Integer id;
   
    private final String name;

    private final String password;

    @EqualsAndHashCode.Exclude
    private final Integer discordId;
    
    @EqualsAndHashCode.Exclude
    private final List<Result> results;

}
