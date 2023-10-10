package projeto.api.utils.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank(message = "name cannot be null")
    private String name;

    @NotBlank(message = "email cannot be null")
    private String email;

    @NotBlank(message = "password cannot be null")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    public User() {}

    public User(String name, String email,String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
