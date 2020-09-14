package com.buzzardsview.readitcheck.security;

import com.buzzardsview.readitcheck.data.UserRepository;
import com.buzzardsview.readitcheck.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppTokenProvider {

    private static final long EXPIRATION_TIME_SECONDS = 864_000_000; // 10 days
    private static final String SECRET = System.getenv("token_secret");
    private static final String HEADER_STRING = "Authorization";

    public static void addAuthentication(HttpServletResponse res, String username) {
        res.addHeader(HEADER_STRING, getToken(username));
    }

    public static String getToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.nanoTime() + EXPIRATION_TIME_SECONDS))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public static Optional<String> getUserFromToken(HttpServletRequest request) {
        final String token = request.getHeader(HEADER_STRING);

        if (token != null) {
            try {
                Claims body = Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(token.replace("", ""))
                        .getBody();
                final Instant expiration = body.getExpiration().toInstant();

                if (expiration.isBefore(Instant.now())) {
                    return Optional.empty();
                }
                return Optional.of(body.getSubject());

            } catch (SignatureException e) {
                // invalid signature
            }
        }
        return Optional.empty();
    }

    public static Long getCurrentUserId(ServletRequest servletRequest) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Long userId = Long.parseLong((String) request.getAttribute("userId"));
        return userId;
    }
}
