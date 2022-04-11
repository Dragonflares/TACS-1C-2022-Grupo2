package com.probasteReiniciando.TPTACS.controllers.dictionary;

import com.probasteReiniciando.TPTACS.functions.JSONWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

public class DictionaryController {

    @RequestMapping({ "/dictionary" })
    public ResponseEntity<JSONWrapper> wordDefinition(@RequestParam String word) {

        return ResponseEntity.ok(new JSONWrapper<>("Everything real good", Collections.singletonList("Dictionary")));
    }


}
