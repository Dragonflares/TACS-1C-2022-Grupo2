package com.probasteReiniciando.TPTACS.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    
    private Integer id;
   
    private String username;

    private String password;

    @EqualsAndHashCode.Exclude
    private Integer discordId;
    
    @EqualsAndHashCode.Exclude
    private final List<Result> results = new ArrayList<>();

}
