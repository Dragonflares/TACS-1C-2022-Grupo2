package com.probasteReiniciando.TPTACS;

import com.probasteReiniciando.TPTACS.dto.user.UserLoginDto;
import com.probasteReiniciando.TPTACS.services.user.JwtUserDetailsService;
import com.probasteReiniciando.TPTACS.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@DependsOn("jedisConnectionFactory")
public class TpTacsApplication {



	public static void main(String[] args) {
		SpringApplication.run(TpTacsApplication.class, args);
	}

}
