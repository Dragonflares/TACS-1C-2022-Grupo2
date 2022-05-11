package com.probasteReiniciando.TPTACS.services.user;

import com.probasteReiniciando.TPTACS.config.ModelMapperTacs;
import com.probasteReiniciando.TPTACS.domain.User;
import com.probasteReiniciando.TPTACS.dto.user.UserLoginDto;
import com.probasteReiniciando.TPTACS.exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Autowired
	private UserService userService;


	@Autowired
	private ModelMapperTacs modelMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<User> user = userService.findByUsername(username);
		if (user.isPresent()) {
			return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(),
					new ArrayList<>());
		} else if ("german".equals(username)) {
			return new org.springframework.security.core.userdetails.User("german", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}


	public User save(UserLoginDto user)  {

		return userService.save(new UserLoginDto(user.getUsername(),
				bcryptEncoder.encode(user.getPassword())));
	}

}