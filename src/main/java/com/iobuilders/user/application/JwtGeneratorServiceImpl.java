package com.iobuilders.user.application;

import com.iobuilders.user.domain.JwtGeneratorService;
import com.iobuilders.user.domain.dto.JwtToken;
import com.iobuilders.user.domain.dto.UserDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtGeneratorServiceImpl implements JwtGeneratorService {
    @Value("${jwt.secret}")
    private String secret;

    @Override
    public JwtToken generateToken(UserDTO user) {
        String jwtToken = Jwts.builder().setSubject(user.getUserName()).setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, secret).compact();
        return new JwtToken(jwtToken);
    }
}
