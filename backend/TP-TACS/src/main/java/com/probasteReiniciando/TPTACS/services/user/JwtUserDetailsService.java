package com.probasteReiniciando.TPTACS.services.user;

import com.probasteReiniciando.TPTACS.domain.UserDao;
import com.probasteReiniciando.TPTACS.dto.user.UserLoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
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

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<UserDao> user = userService.findByUsername(username);
		if (user.isPresent()) {
			return new User(user.get().getName(), user.get().getPassword(),
					new ArrayList<>());
		} else if ("german".equals(username)) {
			return new User("german", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}


	public UserDao save(UserLoginDto user) {
		UserDao newUser = new UserDao();
		newUser.setName(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		return userService.save(newUser);
	}

}