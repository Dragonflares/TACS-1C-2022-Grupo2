package com.probasteReiniciando.TPTACS.domain;

import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result {

    private String username;
    private Integer points;
    private Language language;
    private LocalDate date;

    public Result(Language language, LocalDate now) {
        this.language = language;
        this.date = now;
    }
}