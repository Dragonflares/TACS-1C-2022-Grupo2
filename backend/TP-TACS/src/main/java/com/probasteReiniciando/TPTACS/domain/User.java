package com.probasteReiniciando.TPTACS.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
public class User { // DEBERIA LLAMARSE USER NOMAS PARA MI
    private String name;
    private String password;
    @EqualsAndHashCode.Exclude
    private Integer telegramDiscordId;
    @EqualsAndHashCode.Exclude
    private List<Result> results;
}
