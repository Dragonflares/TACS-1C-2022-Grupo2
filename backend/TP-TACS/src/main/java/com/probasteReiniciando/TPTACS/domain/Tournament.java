package com.probasteReiniciando.TPTACS.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class Tournament {

    @Id
    private String id;

    private String name;

    private Language language;

    private LocalDate startDate;

    private LocalDate endDate;

    private Privacy privacy;

    private User owner;

    @Builder.Default
    private List<User> participants = new ArrayList<>();

    private List<Position> positions;



}
