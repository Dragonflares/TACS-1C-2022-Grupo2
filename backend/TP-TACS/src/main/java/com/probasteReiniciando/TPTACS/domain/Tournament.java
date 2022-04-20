package com.probasteReiniciando.TPTACS.domain;

import lombok.*;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tournament {
    private String name;
    private Language language;
    private Date startDate;
    private Date endDate;
    private Privacy privacy;
    private User owner;
    private List<User> participants;
    private List<Position> positions;


    public Tournament(String prueba, Language spanish) {
        this.name = prueba;
        this.language = spanish;
    }
}
