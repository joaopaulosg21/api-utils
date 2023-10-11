package projeto.api.utils.configuration.auth;

import java.io.IOException;
import java.util.Objects;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import projeto.api.utils.exceptions.LoginException;
import projeto.api.utils.model.User;
import projeto.api.utils.repository.UserRepository;

@RequiredArgsConstructor
public class CustomRequestFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = this.getToken(request);

        if(tokenService.isValid(token)) {
            this.authenticate(token);
        }

        doFilter(request,response,filterChain);
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if(Objects.isNull(token) || !token.startsWith("Bearer ")) {
            return null;
        }

        return token.split(" ")[1];
    }

    private void authenticate(String token) {
        String id = tokenService.getSubject(token);
        User user = userRepository.findById(id).orElseThrow(LoginException::new);

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user,null,null);

        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
