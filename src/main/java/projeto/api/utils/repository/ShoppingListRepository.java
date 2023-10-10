package projeto.api.utils.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.api.utils.model.ShoppingList;

public interface ShoppingListRepository extends JpaRepository<ShoppingList,String> {
}
