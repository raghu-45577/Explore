package com.example.security_app.service.impl;

import com.example.security_app.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${token.signin.key}")
    private String jwtSigninKey;

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolvers) {
        final Claims claims = extractClaims(token);
        return claimResolvers.apply(claims);
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtSigninKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

//    private Key getSigninKey() {
//        byte[] keyBites = Decoders.BASE64.decode(jwtSigninKey);
//        return Keys.hmacShaKeyFor(keyBites);
//    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(),userDetails);
    }

    private String generateToken(HashMap<String,Object> extractClaims, UserDetails userDetails) {

        List<String> roles = new ArrayList<>();
        Map<String, Object> rolesClaim = new HashMap<>();
        userDetails.getAuthorities().forEach(a -> roles.add(a.getAuthority()));
        rolesClaim.put("roles", roles);

        return Jwts.builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(Keys.hmacShaKeyFor(jwtSigninKey.getBytes()),SignatureAlgorithm.HS256)
                .addClaims(rolesClaim)
                .compact();

    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {

        final String username = extractUsername(token);
        return (userDetails.getUsername().equals(username)) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }
}
