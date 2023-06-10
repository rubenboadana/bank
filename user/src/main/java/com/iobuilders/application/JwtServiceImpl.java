package com.iobuilders.application;

import com.iobuilders.domain.JwtService;
import com.iobuilders.domain.dto.JwtToken;
import com.iobuilders.domain.dto.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.secret}")
    private String secret;

    @Override
    public JwtToken generateToken(User user) {
        Instant now = Instant.now();
        String jwtToken = Jwts.builder().claim("username", user.userName())
                .setSubject(user.userName())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(now.plus(60l, ChronoUnit.MINUTES)))
                .signWith(SignatureAlgorithm.HS256, key())
                .compact();

        return new JwtToken(jwtToken);
    }

    @Override
    public boolean validate(String token) {
        Jws<Claims> jwt = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token);

        return true;
    }

    @Override
    public String getUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }
}
