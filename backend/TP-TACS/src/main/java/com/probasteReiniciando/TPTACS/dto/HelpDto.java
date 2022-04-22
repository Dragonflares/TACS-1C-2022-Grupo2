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
    private  Language language;
    private  String yellowWords = "";
    private  String greyWords = "";
    private  HashMap<Integer,String> greenWords;

}
