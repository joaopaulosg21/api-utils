package projeto.api.utils.DTO;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(@NotBlank(message = "email cannot be null") String email,
                       @NotBlank(message = "password cannot be null") String password) {
}
