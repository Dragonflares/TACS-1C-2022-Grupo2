package com.probasteReiniciando.TPTACS.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
public class UserDao { // DEBERIA LLAMARSE USER NOMAS PARA MI
    private String name;
    private String password;
    @EqualsAndHashCode.Exclude
    private Integer telegramDiscordId;
    @EqualsAndHashCode.Exclude
    private List<Result> results;
}
