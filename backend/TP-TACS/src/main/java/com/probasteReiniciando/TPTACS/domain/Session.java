package com.probasteReiniciando.TPTACS.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Session {

    private String id;

    private String username;

    private String token;

}
