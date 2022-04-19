package com.probasteReiniciando.TPTACS.controllers;

import com.probasteReiniciando.TPTACS.dto.PingDto;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin()
public class PingController {

	@RequestMapping({ "/ping" })
	public List<PingDto> hello() {
		return Collections.singletonList(new PingDto("HelloWorld"));
	}

}
