package cf.dmms.app.usermanagement.security.jwt;

import cf.dmms.app.usermanagement.user.principal.UserPrincipalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private JwtTokenProvider jwtTokenProvider;
    private UserPrincipalService userPrincipalService;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserPrincipalService userPrincipalService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userPrincipalService = userPrincipalService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) {
        extractTokenFromHeader(httpServletRequest)
                .filter(token -> jwtTokenProvider.validateToken(token))
                .ifPresent(this::authenticate);

        try {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (Exception e) {
            log.error("Could not set authentication for user", e);
        }
    }

    private Optional<String> extractTokenFromHeader(HttpServletRequest httpServletRequest) {
        String header = httpServletRequest.getHeader("Authorization");
        return getTokenFromRequest(header);
    }

    private Optional<String> getTokenFromRequest(String bearerToken) {
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return Optional.of(bearerToken.substring(7));
        }
        return Optional.empty();
    }

    private void authenticate(String token) {
        UserDetails user = getUserFromToken(token);
        setAuthentication(getAuthenticationForUser(user));
    }

    private UserDetails getUserFromToken(String token) {
        return userPrincipalService.loadUserById(jwtTokenProvider.getUserIdFromToken(token));
    }

    private UsernamePasswordAuthenticationToken getAuthenticationForUser(UserDetails userDetails) {
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    private void setAuthentication(UsernamePasswordAuthenticationToken authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
