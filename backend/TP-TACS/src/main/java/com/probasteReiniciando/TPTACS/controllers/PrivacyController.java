package com.probasteReiniciando.TPTACS.controllers;

import com.probasteReiniciando.TPTACS.domain.Privacy;
import com.probasteReiniciando.TPTACS.dto.PrivacysDto;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@CrossOrigin()
public class PrivacyController {

    @RequestMapping({ "/privacy" })
    public PrivacysDto privacy() {
        return PrivacysDto.builder().privacys(Arrays.asList(Privacy.values())).build();
    }

}
