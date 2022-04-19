package com.probasteReiniciando.TPTACS.controllers.dictionary;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

public class DictionaryController {

    @RequestMapping({ "/dictionary" })
    public List<String> wordDefinition(@RequestParam String word) {

        return Collections.singletonList("Dictionary");
    }


}
