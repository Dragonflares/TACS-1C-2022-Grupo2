package com.probasteReiniciando.TPTACS.controllers;

import com.probasteReiniciando.TPTACS.functions.JSONWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;

public class DictionaryController {

    @RequestMapping({ "/dictionary" })
    public ResponseEntity<JSONWrapper> hello() {

        return ResponseEntity.ok(new JSONWrapper<>("Everything real good", Collections.singletonList("Dictionary")));
    }


}
