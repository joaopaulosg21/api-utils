package projeto.api.utils.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import projeto.api.utils.DTO.ItemDTO;
import projeto.api.utils.util.ItemConverter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "shopping_list")
@Data
@EntityListeners(AuditingEntityListener.class)
public class ShoppingList {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank(message = "name cannot be null")
    private String name;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @CreatedDate
    private LocalDate createdAt;

    @NotNull(message = "items cannot be null")
    @Convert(converter = ItemConverter.class)
    private List<ItemDTO> items;

    private Boolean isPublic;

    @ManyToOne
    @JoinColumn(columnDefinition = "user_id",referencedColumnName = "id")
    private User user;

    public ShoppingList() {}

    public ShoppingList(String name, List<ItemDTO> items, Boolean isPublic) {
        this.name = name;
        this.items = items;
        this.isPublic = isPublic;
    }
}
