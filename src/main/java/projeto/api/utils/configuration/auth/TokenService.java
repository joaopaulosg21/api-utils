package projeto.api.utils.configuration.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import projeto.api.utils.model.User;

import java.util.Date;
import java.util.Objects;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private String exp;

    public String generate(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Date expiration = new Date(new Date().getTime() + Long.parseLong(exp));

        return Jwts.builder()
                .setSubject(user.getId())
                .setExpiration(expiration)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
                .setIssuedAt(new Date())
                .compact();
    }

    public boolean isValid(String token) {
        if(Objects.isNull(token)) return false;

        try {
            Jwts.parserBuilder().setSigningKey(secret.getBytes()).build().parseClaimsJws(token);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    public String getSubject(String token) {
        String subject = Jwts.parserBuilder()
                .setSigningKey(secret.getBytes()).build().parseClaimsJws(token)
                .getBody().getSubject();
        return subject;
    }
}
