package br.com.curso_udemy.product_api.modules.jwt.service;

import br.com.curso_udemy.product_api.config.exception.AuthenticationException;
import br.com.curso_udemy.product_api.modules.jwt.dto.JwtResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class JwtService {

    private static final String BEARER = "bearer ";

    @Value("${app-config.secrets.api-secret}")
    private String apiSecret;

    public void validateAuthorization(String token){
        try {
            var accessToken = extractToken(token);

            SecretKey key = Keys.hmacShaKeyFor(apiSecret.getBytes());

            Claims claims = Jwts
                    .parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(accessToken)
                    .getPayload();
            var user = JwtResponse.getUser(claims);
            if(isEmpty(user) || isEmpty(user.getId())){
                throw new AuthenticationException("The user is not valid.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AuthenticationException("Error while trying to process the Access token.");
        }
    }

    private String extractToken(String token){
        if(isEmpty(token)){
            throw new AuthenticationException("The access token was not informed.");
        }
        if(token.toLowerCase().contains(BEARER)){
            token = token.toLowerCase();
            token = token.replace(BEARER, Strings.EMPTY);
        }
        return token;
    }
}
