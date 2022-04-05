package com.probasteReiniciando.TPTACS.services.user;

import com.probasteReiniciando.TPTACS.dto.TokenDto;
import com.probasteReiniciando.TPTACS.dto.request.LoginRequestDto;

public interface IUserService {
        TokenDto authenticate(LoginRequestDto loginRequestDTO);
}
