package com.probasteReiniciando.TPTACS.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    
    private String id;
   
    private String username;

    private String password;

    private Boolean isAdmin = false;

    @EqualsAndHashCode.Exclude
    private Integer discordId;
    
    @EqualsAndHashCode.Exclude
    private List<Result> results = new ArrayList<>();

}
