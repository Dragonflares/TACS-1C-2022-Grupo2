package com.probasteReiniciando.TPTACS.controllers.help;

import com.probasteReiniciando.TPTACS.functions.JSONWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class HelpController {

    @RequestMapping({ "/help" })
    public List<String> hello() {

        return Collections.singletonList("help");
    }
}
