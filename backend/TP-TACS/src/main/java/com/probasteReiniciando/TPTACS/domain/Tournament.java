package com.probasteReiniciando.TPTACS.domain;

import lombok.Builder;
import lombok.Data;
import java.sql.Date;
import java.util.List;

@Data
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
}
