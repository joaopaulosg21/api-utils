package projeto.api.utils.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.api.utils.model.ShoppingList;

import java.util.List;
import java.util.Optional;

public interface ShoppingListRepository extends JpaRepository<ShoppingList,String> {
    List<ShoppingList> findAllByIsPublic(boolean b);

    List<ShoppingList> findAllByIsPublicAndUserId(boolean b, String userId);

    Optional<ShoppingList> findByIdAndUserId(String id, String userId);
}
