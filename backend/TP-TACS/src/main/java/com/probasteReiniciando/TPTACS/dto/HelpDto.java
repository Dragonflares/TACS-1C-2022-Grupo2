package com.probasteReiniciando.TPTACS.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class HelpDto {
    private final String wordToHelp;
}
