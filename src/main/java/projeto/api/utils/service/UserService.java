package projeto.api.utils.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import projeto.api.utils.configuration.auth.TokenService;
import projeto.api.utils.dto.LoginDTO;
import projeto.api.utils.dto.TokenResponseDTO;
import projeto.api.utils.exceptions.EmailAlreadyUsedException;
import projeto.api.utils.model.User;
import projeto.api.utils.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    public User create(User user) {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());

        if(optionalUser.isPresent()) {
            throw new EmailAlreadyUsedException();
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public TokenResponseDTO login(LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken usernamePasswordToken = new UsernamePasswordAuthenticationToken(loginDTO,loginDTO,null);
        Authentication authentication = authenticationManager.authenticate(usernamePasswordToken);
        String token = tokenService.generate(authentication);
        return new TokenResponseDTO("Bearer",token);
    }
}
