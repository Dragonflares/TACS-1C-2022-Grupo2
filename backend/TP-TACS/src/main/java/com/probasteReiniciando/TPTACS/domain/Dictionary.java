package com.probasteReiniciando.TPTACS.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class Dictionary {

    private final List<WordFinder> wordFinder = new ArrayList<>();

}
