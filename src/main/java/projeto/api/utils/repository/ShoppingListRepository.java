package projeto.api.utils.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.api.utils.model.ShoppingList;

import java.util.List;

public interface ShoppingListRepository extends JpaRepository<ShoppingList,String> {
    List<ShoppingList> findAllByIsPublic(boolean b);
}
