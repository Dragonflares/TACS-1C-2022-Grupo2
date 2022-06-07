package com.probasteReiniciando.TPTACS.dao;

import com.probasteReiniciando.TPTACS.domain.Language;
import com.probasteReiniciando.TPTACS.domain.Privacy;
import com.probasteReiniciando.TPTACS.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class TournamentDAO {

    @Id
    private String id;

    private String name;

    private Language language;

    private LocalDate startDate;

    private LocalDate endDate;

    private Privacy privacy;

    @DBRef
    private UserDAO owner;

    @Builder.Default
    private List<UserDAO> participants = new ArrayList<>();


}
