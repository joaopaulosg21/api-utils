package projeto.api.utils.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projeto.api.utils.model.ShoppingList;
import projeto.api.utils.model.User;
import projeto.api.utils.repository.ShoppingListRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingListService {
    private final ShoppingListRepository shoppingListRepository;

    public ShoppingList create(User user, ShoppingList shoppingList) {
        shoppingList.setUser(user);
        return shoppingListRepository.save(shoppingList);
    }

    public List<ShoppingList> findAllPublic() {
        return shoppingListRepository.findAllByIsPublic(true);
    }
}
