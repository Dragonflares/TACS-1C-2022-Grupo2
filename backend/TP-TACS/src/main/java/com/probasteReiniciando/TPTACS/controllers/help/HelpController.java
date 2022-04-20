package com.probasteReiniciando.TPTACS.controllers.help;

import com.probasteReiniciando.TPTACS.functions.JSONWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
public class HelpController {

    @GetMapping(path="/help",produces = "application/json")
    public ResponseEntity<JSONWrapper> hello() {

        return ResponseEntity.ok(new JSONWrapper<>("Everything real good", Collections.singletonList("help")));
    }
}
