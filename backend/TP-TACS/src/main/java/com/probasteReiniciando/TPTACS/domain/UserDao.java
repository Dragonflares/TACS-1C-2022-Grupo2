package com.probasteReiniciando.TPTACS.domain;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDao { // DEBERIA LLAMARSE USER NOMAS PARA MI
    private String name;
    @EqualsAndHashCode.Exclude
    private String password;
    @EqualsAndHashCode.Exclude
    private Integer telegramDiscordId;
    @EqualsAndHashCode.Exclude
    private List<Result> results;
}
