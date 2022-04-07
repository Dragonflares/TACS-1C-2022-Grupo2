/*
package com.probasteReiniciando.TPTACS.controllers;

import com.probasteReiniciando.TPTACS.Utils.JwtTokenProvider;
import com.probasteReiniciando.TPTACS.dto.TokenDto;
import com.probasteReiniciando.TPTACS.dto.request.LoginRequestDto;
import com.probasteReiniciando.TPTACS.services.user.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins ="*",maxAge = 3600)
@RestController
public class AuthenticationController {
    private JwtTokenProvider jwtTokenProvider;
    private IUserService userService;
    private AuthenticationManager authenticationManager;

    public AuthenticationController(IUserService userService, JwtTokenProvider jwtTokenProvider,
                                    AuthenticationManager authenticationManager)
    {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequest)
    {
        this.authenticate(loginRequest);
        TokenDto tokenResponse = userService.authenticate(loginRequest);
        return ResponseEntity.ok(tokenResponse);
    }

    private void authenticate(LoginRequestDto loginRequest){
        String password = loginRequest.getPassword();

        Authentication auth =  authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        password
                )
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}*/
