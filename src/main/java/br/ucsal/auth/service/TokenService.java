package br.ucsal.auth.service;

import br.ucsal.auth.user.model.entity.User;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    private static final String ISSUER = "auth-api";

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            var token = JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(user.getUsername())
                    .withClaim("id", user.getId())
                    .withClaim("role", "ROLE_" + user.getRole().name())
                    .withExpiresAt(genExpireAt());

            if (user.getProfessorId() != null) {
                token.withClaim("professorId", user.getProfessorId());
            }

            return token.sign(algorithm);

        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro na geração do Token", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    private Instant genExpireAt() {
        return LocalDateTime.now().plusDays(3).toInstant(ZoneOffset.of("-03:00"));
    }

}
