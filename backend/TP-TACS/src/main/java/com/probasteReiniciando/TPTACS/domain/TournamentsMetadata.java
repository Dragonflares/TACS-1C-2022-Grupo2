package com.probasteReiniciando.TPTACS.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TournamentsMetadata {

    private Integer publicTournamentsQuantity;
    private Integer privateTournamentsQuantity;
    private List<String> privacys;

}
