package com.probasteReiniciando.TPTACS.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Position {
    private User user;
    private Integer points;
}

