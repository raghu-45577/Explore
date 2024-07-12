package com.explore.gateway.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${token.signin.key}")
    private String jwtSigninKey;


    public Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSigninKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolvers) {
        final Claims claims = validateToken(token);
        return claimResolvers.apply(claims);
    }

    public String extractRoles(String token){
        Claims allClaims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSigninKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
        List<String> roles = allClaims.get("roles", List.class);
        StringBuilder authorities = new StringBuilder(roles.get(0));

        for(int i=1;i<roles.size();i++){
            authorities.append(",").append(roles.get(i));
        }

        return authorities.toString();
    }
}
