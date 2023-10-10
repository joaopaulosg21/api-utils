package projeto.api.utils.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import projeto.api.utils.configuration.auth.CustomRequestFilter;
import projeto.api.utils.configuration.auth.TokenService;
import projeto.api.utils.repository.UserRepository;

import java.util.Arrays;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests -> {
            requests.requestMatchers(HttpMethod.POST,"/api/users/").permitAll()
                    .requestMatchers(HttpMethod.POST,"/api/users/login").permitAll()
                    .requestMatchers(HttpMethod.GET,"/api/list/find/public").permitAll()
                    .anyRequest().authenticated();
        })
                .csrf(AbstractHttpConfigurer::disable)
                .cors((cors) -> {
                    CorsConfiguration corsConfig = new CorsConfiguration();
                    corsConfig.setAllowedOrigins(Arrays.asList("*"));
                    corsConfig.setMaxAge(8000L);
                    corsConfig.addAllowedHeader("*");
                    corsConfig.addAllowedMethod("*");
                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    source.registerCorsConfiguration("/**", corsConfig);
                    cors.configurationSource(source);
                })
                .addFilterBefore(new CustomRequestFilter(tokenService,userRepository), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new CustomAuthenticationManager();
    }
}
