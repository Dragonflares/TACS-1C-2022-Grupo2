package com.probasteReiniciando.TPTACS.controllers;

import com.probasteReiniciando.TPTACS.domain.Privacy;
import com.probasteReiniciando.TPTACS.dto.PingDto;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin()
public class PrivacyController {

    @RequestMapping({ "/privacy" })
    public List<Privacy> privacy() {
        return Arrays.asList(Privacy.values());
    }
}
