package com.probasteReiniciando.TPTACS.controllers.help;

import com.probasteReiniciando.TPTACS.functions.JSONWrapper;
import com.probasteReiniciando.TPTACS.functions.WordFileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class HelpController {

    @Autowired
    WordFileReader wordFileReader;

    @RequestMapping({ "/help" })
    public List<String> help() {

        return wordFileReader.find();
    }
}
