package projeto.api.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import projeto.api.utils.exceptions.EmailAlreadyUsedException;
import projeto.api.utils.model.User;
import projeto.api.utils.repository.UserRepository;
import projeto.api.utils.service.UserService;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserUnitTests {

    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;

    @BeforeEach
    void setup() {
        this.userService = new UserService(userRepository,passwordEncoder,authenticationManager);
    }

    @Test
    void createUserTest() {
        User user = new User("test name", "test@email.com","123");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encoded password");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User response = userService.create(user);

        assertEquals(user.getEmail(),response.getEmail());
    }

    @Test
    void createUserExceptionTest() {
        User user = new User("test name", "test@email.com","123");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        EmailAlreadyUsedException exception = assertThrows(EmailAlreadyUsedException.class, () -> userService.create(user));

        assertEquals("Email already used",exception.getMessage());
    }
}
