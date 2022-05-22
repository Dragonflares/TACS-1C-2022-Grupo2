package com.probasteReiniciando.TPTACS.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tournament {

    private Integer id;

    private String name;

    private Language language;

    private LocalDate startDate;

    private LocalDate endDate;

    private Privacy privacy;

    private User owner;

    @Builder.Default
    private List<User> participants = new ArrayList<>();

    private List<Position> positions;

    private List<Result> results;


    public Tournament(String prueba, Language spanish) {
        this.name = prueba;
        this.language = spanish;
    }

}
