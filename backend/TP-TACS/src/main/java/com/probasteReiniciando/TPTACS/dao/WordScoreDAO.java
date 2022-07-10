package com.probasteReiniciando.TPTACS.dao;

import com.probasteReiniciando.TPTACS.domain.Language;
import com.probasteReiniciando.TPTACS.dto.LanguagesDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class WordScoreDAO {

    @Id
    private String id;

    private String word;

    private Integer score;

    private Language language;


}