package com.probasteReiniciando.TPTACS.controllers.dictionary;

import com.probasteReiniciando.TPTACS.functions.JSONWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class DictionaryController {

    @RequestMapping({ "/dictionary" })
    public List<String> wordDefinition(@RequestParam String word) {

        return Collections.singletonList("Dictionary");
    }


}
