package SSU.SSU_Meet_BE.Security;

import SSU.SSU_Meet_BE.Exception.JwtExceptions;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Order(0)
@RequiredArgsConstructor
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            log.info("아바이시발");
            String token = parseBearerToken(request);
            log.info("그냥 개조같네");
            User user = parseUserSpecification(token);
            AbstractAuthenticationToken authenticated = UsernamePasswordAuthenticationToken.authenticated(user, token, user.getAuthorities());
            authenticated.setDetails(new WebAuthenticationDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticated);

            filterChain.doFilter(request, response);
        } catch (JwtExceptions exceptions) {
            log.info("어미시발");
            throw new JwtExceptions("doFilterInternal error"); // 토큰 에러
        }

    }

    public String parseBearerToken(HttpServletRequest request) {
        try {
            return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                    .filter(token -> token.substring(0, 7).equalsIgnoreCase("Bearer "))
                    .map(token -> token.substring(7))
                    .orElse(null);
        } catch (JwtExceptions e) {
            throw new JwtExceptions("parseBearerToken error"); // 토큰 에러
        }
    }

    private User parseUserSpecification(String token) {
        try {
            log.info("!@!@!@");
            String[] split = Optional.ofNullable(token)
                    .filter(subject -> subject.length() >= 10)
                    .map(tokenProvider::validateTokenAndGetSubject)
                    .orElse("anonymous:anonymous")
                    .split(":");
            log.info("()()())");
            return new User(split[0], "", List.of(new SimpleGrantedAuthority(split[1])));
        } catch (JwtExceptions e) {
            throw new JwtExceptions("parseUserSpecification error"); // 토큰 에러
        }

    }
}
