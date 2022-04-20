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
    private UserDao owner;
    private List<UserDao> participants;
    private List<Position> positions;


    public Tournament(String prueba, Language spanish) {
        this.name = prueba;
        this.language = spanish;
    }
}
