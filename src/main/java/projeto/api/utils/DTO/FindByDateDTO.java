package projeto.api.utils.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record FindByDateDTO(
        @NotNull(message = "time cannot be null")
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime time) {
}
