package com.probasteReiniciando.TPTACS.Utils;


import com.probasteReiniciando.TPTACS.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
@NoArgsConstructor
public class JwtTokenProvider {
    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";
    private final String SECRET = "mySecretKey";
    private final Integer minutesTillExpiration = 1440; //1 day, should be in config file

    public String doGenerateToken(User user) {

        Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Instant expiration = issuedAt.plus(minutesTillExpiration, ChronoUnit.MINUTES);

        return Jwts.builder()
                .setIssuedAt(Date.from(issuedAt))
                .setExpiration(Date.from(expiration))
                .signWith(SignatureAlgorithm.HS256, SECRET.getBytes())
                .compact();
    }



}
