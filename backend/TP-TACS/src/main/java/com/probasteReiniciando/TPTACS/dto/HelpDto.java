package com.probasteReiniciando.TPTACS.dto;

import com.probasteReiniciando.TPTACS.domain.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;

@Data
@AllArgsConstructor
@Builder
public class HelpDto {
    private final String wordToHelp;
    private final Language language;
    private final String yellowWords;
    private final String greyWords;
    private final HashMap<Integer,String> greenWords;

}
