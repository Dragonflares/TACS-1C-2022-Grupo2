package com.probasteReiniciando.TPTACS.controllers.jwtauthentication;

import com.probasteReiniciando.TPTACS.domain.User;
import com.probasteReiniciando.TPTACS.dto.ResponseDto;
import com.probasteReiniciando.TPTACS.dto.TokenDto;
import com.probasteReiniciando.TPTACS.exceptions.UnAuthorizedException;
import com.probasteReiniciando.TPTACS.exceptions.UserNotFoundException;
import com.probasteReiniciando.TPTACS.services.user.BlackListingService;
import com.probasteReiniciando.TPTACS.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blacklist")
public class BlackListController {

    @Autowired
    private BlackListingService blackListingService;

    @Autowired
    private UserService userService;


    @PostMapping(path = "/token" , produces = "application/json")
    public ResponseDto logout(@RequestBody TokenDto token, @RequestAttribute(name="userAttributeName") String userLoggedIn) {
        User user = userService.findByUsername(userLoggedIn).orElseThrow(() -> new UserNotFoundException(userLoggedIn));
        if (!user.getIsAdmin()) {
            throw new UnAuthorizedException(userLoggedIn);
        }
        blackListingService.getJwtBlackList(token.getToken());
        blackListingService.blackListJwt(token.getToken());
        return ResponseDto.builder().response("token added to blacklist").build();
    }

}