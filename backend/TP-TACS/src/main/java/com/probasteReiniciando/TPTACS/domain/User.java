package com.probasteReiniciando.TPTACS.domain;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class User {
    private String name;
    private String password;
    private Integer telegramDiscordId;
    private List<Result> results;
}
