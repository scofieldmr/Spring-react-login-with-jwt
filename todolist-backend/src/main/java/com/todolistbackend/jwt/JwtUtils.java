package com.todolistbackend.jwt;

import com.todolistbackend.entity.CustomUserDetails;
import com.todolistbackend.service.CustomUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;


@Component
public class JwtUtils {

    private static Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${authentication.jwt.jwtSecretKey}")
    private String jwSecretKey;

    @Value(("${authentication.jwt.jwtExpirationInMs}"))
    private int jwtExpiration;

    public Key getJwSecretKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwSecretKey));
    }

    //To generate the jwt key
    public String generateJwtToken(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        String username = customUserDetails.getUsername();

        String role = customUserDetails.getAuthorities().toString();

        Date dateExpiration = new Date(new Date().getTime() + jwtExpiration);

        String jwtToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .claim("roles",role)
                .setExpiration(dateExpiration)
                .signWith(getJwSecretKey())
                .compact();
        logger.info("JWT Token : {} ", jwtToken);

        return jwtToken;
    }

    //To get the username from the jwtToken
    public String getUsernameFromJwtToken(String token) {
        String username =  Jwts.parserBuilder()
                .setSigningKey(getJwSecretKey()).build()
                .parseClaimsJws(token).getBody().getSubject();

        logger.info("Get Username from the Jwt Token : {} ", username);

        return username;
    }

    //To get the Role from the jwtToken
    public String getRoleFromJwtToken(String token) {
        String roles =  Jwts.parserBuilder()
                .setSigningKey(getJwSecretKey()).build()
                .parseClaimsJws(token)
                .getBody()
                .get("roles",String.class);

        logger.info("Get Role from the Jwt Token : {} ", roles);

        return roles;
    }

    //To get the expiration time
    public Date getExpirationDateFromJwtToken(String token) {
        Date expirationTime = Jwts.parserBuilder()
                .setSigningKey(getJwSecretKey()).build()
                .parseClaimsJws(token)
                .getBody().getExpiration();

        logger.info("Get ExpirationDate from the Jwt Token : {} ", expirationTime);
        return expirationTime;
    }

    //To vlaidate the Jwt Token
    public boolean validateJwtToken(String authToken) {
        try{
            Jwts.parserBuilder()
                    .setSigningKey(getJwSecretKey()).build().parseClaimsJws(authToken);
            return true;
        }
        catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }


}
