package com.probasteReiniciando.TPTACS.dto;

import com.probasteReiniciando.TPTACS.domain.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WordScoreDto {

    private String word;

    private Integer score;

    private Language language;


}
