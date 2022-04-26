package com.probasteReiniciando.TPTACS.controllers.jwtauthentication;


import com.probasteReiniciando.TPTACS.config.JwtTokenUtil;
import com.probasteReiniciando.TPTACS.domain.User;
import com.probasteReiniciando.TPTACS.dto.JwtRequest;
import com.probasteReiniciando.TPTACS.dto.JwtResponse;
import com.probasteReiniciando.TPTACS.dto.user.UserDto;
import com.probasteReiniciando.TPTACS.dto.user.UserLoginDto;
import com.probasteReiniciando.TPTACS.exceptions.UserNotFoundException;
import com.probasteReiniciando.TPTACS.services.user.JwtUserDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import java.util.Objects;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping(value = "/accesstoken", method = RequestMethod.POST)
	public JwtResponse createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
			throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = jwtInMemoryUserDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return new JwtResponse(token);
	}

	@RequestMapping(value = "/authorization", method = RequestMethod.POST)
	public UserDto saveUser(@RequestBody UserLoginDto user) throws Exception {
		return modelMapper.map(userDetailsService.save(user), UserDto.class);
	}

	private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new UserNotFoundException(username);
		} catch (BadCredentialsException e) {
			throw new UserNotFoundException(username);
		}
	}
}
