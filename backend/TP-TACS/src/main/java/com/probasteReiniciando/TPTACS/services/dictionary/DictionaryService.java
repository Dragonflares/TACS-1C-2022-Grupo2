package com.probasteReiniciando.TPTACS.services.dictionary;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@Data
@Service
public class DictionaryService {

    private  WordFinder wordFinder = new WebWordFinder();

}
