package com.probasteReiniciando.TPTACS.dto;

import com.probasteReiniciando.TPTACS.domain.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LanguagesDto {
    private List<Language> languages;
}
