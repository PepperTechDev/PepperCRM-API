package peppertech.crm.api.Security.Config.Filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import peppertech.crm.api.Security.Service.JwtService;
import peppertech.crm.api.Users.Model.DTO.UserDTO;
import peppertech.crm.api.Users.Service.SUser;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    // TODO: implement role for jwt

    private final JwtService jwtService;
    private final SUser serviceUser;

    public JwtAuthFilter(JwtService jwtService, SUser serviceUser) {
        this.jwtService = jwtService;
        this.serviceUser = serviceUser;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        try {
            UserDTO user = jwtService.validateAuthHeader(authHeader);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    user, user.getPassword(), null /* user.getAuthorities() */);
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        } catch (Exception ignored) {
        }
        filterChain.doFilter(request, response);
    }
}