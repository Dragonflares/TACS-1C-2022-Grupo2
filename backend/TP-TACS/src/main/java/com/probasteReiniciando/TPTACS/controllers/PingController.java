package com.probasteReiniciando.TPTACS.controllers;

import com.probasteReiniciando.TPTACS.dto.PingDto;
import com.probasteReiniciando.TPTACS.functions.JSONWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin()
public class PingController {

	@RequestMapping({ "/ping" })
	public ResponseEntity<JSONWrapper> hello() {
		return ResponseEntity.ok(new JSONWrapper<>("Everything real good",
				Collections.singletonList(new PingDto("HelloWorld"))));
	}

}
