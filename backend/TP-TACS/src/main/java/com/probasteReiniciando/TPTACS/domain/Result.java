package com.probasteReiniciando.TPTACS.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Result {
    private User user;
    private Integer points;
    private Language language;
    private LocalDate date;
}
