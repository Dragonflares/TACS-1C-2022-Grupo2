package com.probasteReiniciando.TPTACS.domain;

import lombok.Builder;
import lombok.Data;
import java.sql.Date;
import java.util.List;

@Data
@Builder
public class Tournament {
    private final String name;
    private final Language language;
    private final Date startDate;
    private final Date endDate;
    private final Privacy privacy;
    private final User owner;
    private final List<User> participants;
    private final List<Position> positions;
}
