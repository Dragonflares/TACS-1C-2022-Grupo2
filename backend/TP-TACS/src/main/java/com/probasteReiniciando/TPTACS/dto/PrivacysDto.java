package com.probasteReiniciando.TPTACS.dto;

import com.probasteReiniciando.TPTACS.domain.Privacy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class PrivacysDto {

    private List<Privacy> privacys;

}
