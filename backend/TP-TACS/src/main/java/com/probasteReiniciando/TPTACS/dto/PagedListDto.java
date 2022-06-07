package com.probasteReiniciando.TPTACS.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PagedListDto <T> {
    List<T> elements;
    Long totalCount;
}
