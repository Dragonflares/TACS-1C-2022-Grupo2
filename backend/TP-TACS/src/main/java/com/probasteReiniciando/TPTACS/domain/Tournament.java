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
    private UserDao owner;
    private List<UserDao> participants;
    private List<Position> positions;
}
