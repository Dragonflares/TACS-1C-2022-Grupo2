package com.probasteReiniciando.TPTACS.controllers.help;

import com.probasteReiniciando.TPTACS.functions.JSONWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
public class HelpController {

    @RequestMapping({ "/help" })
    public JSONWrapper hello() {

        return new JSONWrapper<>("Everything real good", Collections.singletonList("help"));
    }
}
