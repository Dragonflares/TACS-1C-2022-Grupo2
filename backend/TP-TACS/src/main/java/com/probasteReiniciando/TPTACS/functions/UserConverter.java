package com.probasteReiniciando.TPTACS.functions;

import com.probasteReiniciando.TPTACS.domain.User;
import com.probasteReiniciando.TPTACS.dto.user.UserDto;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class UserConverter {
    public  UserDto convertUserToDto(User user){
        return UserDto.builder()
                .name(user.getName())
                .build();
    }
}
