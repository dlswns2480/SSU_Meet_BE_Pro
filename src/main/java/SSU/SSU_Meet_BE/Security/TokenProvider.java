package SSU.SSU_Meet_BE.Security;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
public class TokenProvider {
    private final String secretKey;
    private final long access_expirationHours;
    private final long refresh_expirationHours;
    private final String issuer;

    public TokenProvider(
            @Value("${secret-key}") String secretKey,
            @Value("${access-expiration-hours}") long access_expirationHours,
            @Value("${refresh-expiration-hours}") long refresh_expirationHours,
            @Value("${issuer}") String issuer
    ) {
        this.secretKey = secretKey;
        this.access_expirationHours = access_expirationHours;
        this.refresh_expirationHours = refresh_expirationHours;
        this.issuer = issuer;
    }

    public String createAccessToken(String userSpecification) {
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더 typ : JWT
                .signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName()))   // HS512 알고리즘을 사용하여 secretKey를 이용해 서명
                .setSubject(userSpecification)  // JWT 토큰 제목 (내용 SUB)
                .setIssuer(issuer)  // JWT 토큰 발급자
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))    // JWT 토큰 발급 시간
                .setExpiration(Date.from(Instant.now().plus(access_expirationHours, ChronoUnit.HOURS)))    // JWT 토큰 만료 시간
                .compact(); // JWT 토큰 생성
    }

    //refresh 토큰 제작하는 함수 작성     //현규
    public String createRefreshToken(String studentNumber) {
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName()))
                .setSubject(studentNumber)  // Use studentNumber as the subject of the token
                .setIssuer(issuer)
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
                .setExpiration(Date.from(Instant.now().plus(refresh_expirationHours, ChronoUnit.HOURS)))
                .compact();
    }

    public String validateTokenAndGetSubject(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes()) //비밀값으로 복호화
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey.getBytes())
                    .build()
                    .parseClaimsJws(token);
            return true; // Token is valid
        } catch (Exception e) {
            return false; // Token validation failed
        }
    }




}