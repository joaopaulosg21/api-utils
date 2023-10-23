package projeto.api.utils.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CreateDailyTaskDTO{
        @NotBlank(message = "description cannot be null")
        String description;

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime time;

        boolean everyDay;

        boolean completed;

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime end_date;
}
