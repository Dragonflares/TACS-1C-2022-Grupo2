package com.probasteReiniciando.TPTACS.services.user;

import com.probasteReiniciando.TPTACS.dto.TokenDto;
import com.probasteReiniciando.TPTACS.dto.request.LoginRequestDto;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService{
    @Override
    public TokenDto authenticate(LoginRequestDto loginRequestDTO) {
        return null;
    }
}
