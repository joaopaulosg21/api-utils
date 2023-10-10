package projeto.api.utils.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projeto.api.utils.exceptions.ShoppingListNotFoundException;
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

    public List<ShoppingList> findAllPrivate(User user) {
        return shoppingListRepository.findAllByIsPublicAndUserId(false,user.getId());
    }

    public ShoppingList findById(String id, User user) {
        return shoppingListRepository.findByIdAndUserId(id,user.getId())
                .orElseThrow(ShoppingListNotFoundException::new);
    }
}
