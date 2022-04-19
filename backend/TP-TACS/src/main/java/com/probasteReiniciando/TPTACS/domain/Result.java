package com.probasteReiniciando.TPTACS.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Result {
    private final User user;
    private final Integer points;
    private final Language language;
    private final LocalDate date;
}
