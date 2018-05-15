package com.crm.application.utilModels.auth;

import com.crm.application.model.User;
import com.crm.application.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

@Service
public class TokenUtil {

    private static final Logger log = LoggerFactory.getLogger(TokenUtil.class);
    private static final long VALIDITY_TIME_MS = 2 * 60 * 60 * 1000; // 2 hours  validity
    private static final String AUTH_HEADER_NAME = "Authorization";
    private final UserRepository userRepository;
    private String secret = "mrin";

    public TokenUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<Authentication> verifyToken(HttpServletRequest request) {
        final String token = request.getHeader(AUTH_HEADER_NAME);

        if (token != null && !token.isEmpty()) {
            final TokenUser user = parseUserFromToken(token.replace("Bearer", "").trim());
            if (user != null) {
                return Optional.of(new UserAuthentication(user));
            }
        }
        return Optional.empty();

    }

    public TokenUser parseUserFromToken(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        User user = userRepository.findOne(Long.valueOf(String.valueOf(claims.get("userId"))));
        return new TokenUser(user);
    }

    public String createTokenForUser(TokenUser tokenUser) {
        return createTokenForUser(tokenUser.getUser());
    }

    private String createTokenForUser(User user) {
        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + VALIDITY_TIME_MS))
                .setSubject(user.getName() + user.getSurname())
                .claim("userId", user.getId())
                .claim("role", user.getRole().toString())
                .claim("login", user.getLogin())
                .claim("passwordHash", user.getPasswordHash())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

    }

}
