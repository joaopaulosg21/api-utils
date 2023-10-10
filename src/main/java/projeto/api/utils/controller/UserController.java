package projeto.api.utils.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import projeto.api.utils.DTO.LoginDTO;
import projeto.api.utils.DTO.TokenResponseDTO;
import projeto.api.utils.model.User;
import projeto.api.utils.service.UserService;

@RestController
@RequestMapping("/api/users/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(user));
    }

    @PostMapping("login")
    public ResponseEntity<TokenResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(userService.login(loginDTO));
    }

    @GetMapping()
    public ResponseEntity<Object> test(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(user);
    }
}
