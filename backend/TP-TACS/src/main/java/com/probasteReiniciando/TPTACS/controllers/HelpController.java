package com.probasteReiniciando.TPTACS.controllers;

import com.probasteReiniciando.TPTACS.functions.JSONWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
public class HelpController {

    @RequestMapping({ "/help" })
    public ResponseEntity<JSONWrapper> hello() {

        return ResponseEntity.ok(new JSONWrapper<>("Everything real good", Collections.singletonList("help")));
    }
}