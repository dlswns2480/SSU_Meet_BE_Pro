package SSU.SSU_Meet_BE.Security;

import SSU.SSU_Meet_BE.Exception.InvalidTokenException;
import SSU.SSU_Meet_BE.Exception.TokenExpiredException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import javax.crypto.spec.SecretKeySpec;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@PropertySource("classpath:jwt.yaml")
@Service
@Slf4j
public class TokenProvider {
    private final String secretKey;
    private final long expirationMinutes;
    private final long refreshExpirationMinutes; // 추가 : 리프레시 토큰 유효 기간
    private final String issuer;

    public TokenProvider(
            @Value("${secret-key}") String secretKey,
            @Value("${expiration-minutes}") long expirationMinutes,
            @Value("${refresh-expiration-minutes}") long refreshExpirationMinutes,
            @Value("${issuer}") String issuer
    ) {

        this.secretKey = secretKey;
        this.expirationMinutes = expirationMinutes ;
        this.refreshExpirationMinutes = refreshExpirationMinutes;
        this.issuer = issuer;
    }

    public String createToken(String userSpecification) {
        return Jwts.builder()
                .signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName()))   // HS512 알고리즘을 사용하여 secretKey를 이용해 서명
                .setSubject(userSpecification)  // JWT 토큰 제목
                .setIssuer(issuer)  // JWT 토큰 발급자
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))    // JWT 토큰 발급 시간
                .setExpiration(Date.from(Instant.now().plus(expirationMinutes, ChronoUnit.MINUTES)))    // JWT 토큰 만료 시간
                .compact(); // JWT 토큰 생성
    }

    public String createRefreshToken(String userSpecification) {
        return Jwts.builder()
                .signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName()))
                .setSubject(userSpecification)
                .setIssuer(issuer)
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
                .setExpiration(Date.from(Instant.now().plus(refreshExpirationMinutes, ChronoUnit.MINUTES)))
                .compact();
    }

    public String validateTokenAndGetSubject(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // 만료 시간 확인
            Date expiration = claims.getExpiration();
            if (expiration != null && expiration.after(new Date())) {
                return claims.getSubject();
            } else {
                log.info("%%%");
                throw new TokenExpiredException("Token has expired"); // 토큰 만료됨
            }
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException("Token has expired"); // 토큰 만료됨
        } catch (MalformedJwtException | SecurityException | IllegalArgumentException e) {
            throw new InvalidTokenException("Invalid token");
        } catch (JwtException e) {
            throw new InvalidTokenException("Token error");
        }


    }

    public String validateRefreshTokenAndGetSubject(String refreshToken) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey.getBytes())
                    .build()
                    .parseClaimsJws(refreshToken)
                    .getBody();

            if (!claims.getIssuer().equals(issuer)) {
                throw new InvalidTokenException("Invalid issuer");
            }

            Date expiration = claims.getExpiration();
            if (expiration == null || expiration.before(new Date())) {
                throw new TokenExpiredException("Token expired");
            }

            return claims.getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException("Invalid token");
        }
    }
}

