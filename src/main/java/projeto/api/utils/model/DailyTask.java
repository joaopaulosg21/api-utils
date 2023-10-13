package projeto.api.utils.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "daily_task")
@Data
public class DailyTask {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank(message = "description cannot be null")
    private String description;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime time;

    private boolean everyDay;

    private boolean completed;

    @ManyToOne
    @JoinColumn(columnDefinition = "user_id",referencedColumnName = "id")
    private User user;
}
