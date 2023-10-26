package projeto.api.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;

import projeto.api.utils.DTO.DefaultResponseDTO;
import projeto.api.utils.DTO.ItemDTO;
import projeto.api.utils.exceptions.ShoppingListNotFoundException;
import projeto.api.utils.model.ShoppingList;
import projeto.api.utils.model.User;
import projeto.api.utils.repository.ShoppingListRepository;
import projeto.api.utils.service.ShoppingListService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ShoppingListUnitTests {

    private ShoppingListService shoppingListService;

    @Mock
    private ShoppingListRepository shoppingListRepository;

    @BeforeEach
    void setup() {
        this.shoppingListService = new ShoppingListService(shoppingListRepository);
    }

    @Test
    void createShoppingListTest() {
        User user = new User("test name", "test@email.com", "123");
        List<ItemDTO> list = Arrays.asList(new ItemDTO("Item 01", "10"));
        ShoppingList shoppingList = new ShoppingList("list name", list, true);

        when(shoppingListRepository.save(any(ShoppingList.class))).thenReturn(shoppingList);

        ShoppingList response = shoppingListService.create(user, shoppingList);

        assertEquals(user.getEmail(), response.getUser().getEmail());
        assertEquals(list.get(0).name(), response.getItems().get(0).name());
    }

    @Test
    void findAllPublicTest() {
        List<ItemDTO> list = Arrays.asList(new ItemDTO("Item 01", "10"));
        ShoppingList shoppingList = new ShoppingList("list name", list, true);

        when(shoppingListRepository.findAllByIsPublic(true)).thenReturn(Arrays.asList(shoppingList));

        List<ShoppingList> response = shoppingListService.findAllPublic();

        assertEquals(shoppingList.getName(), response.get(0).getName());
        assertTrue(shoppingList.getIsPublic());
    }

    @Test
    void findAllPrivateTest() {
        User user = new User("test name", "test@email.com", "123");
        user.setId("id");
        List<ItemDTO> list = Arrays.asList(new ItemDTO("Item 01", "10"));
        ShoppingList shoppingList = new ShoppingList("list name", list, false);

        when(shoppingListRepository.findAllByIsPublicAndUserId(anyBoolean(), anyString()))
                .thenReturn(Arrays.asList(shoppingList));

        List<ShoppingList> response = shoppingListService.findAllPrivate(user);

        assertEquals(shoppingList.getName(), response.get(0).getName());
        assertFalse(shoppingList.getIsPublic());
    }

    @Test
    void findByIdTest() {
        User user = new User("test name", "test@email.com", "123");
        user.setId("id");
        List<ItemDTO> list = Arrays.asList(new ItemDTO("Item 01", "10"));
        ShoppingList shoppingList = new ShoppingList("list name", list, false);
        shoppingList.setId("list-id");

        when(shoppingListRepository.findByIdAndUserId(anyString(), anyString())).thenReturn(Optional.of(shoppingList));

        ShoppingList response = shoppingListService.findById("list-id", user);

        assertEquals(shoppingList.getId(), response.getId());
    }

    @Test
    void findByIdExceptionTest() {
        User user = new User("test name", "test@email.com", "123");
        user.setId("id");

        when(shoppingListRepository.findByIdAndUserId(anyString(), anyString())).thenReturn(Optional.empty());

        ShoppingListNotFoundException exception = assertThrows(ShoppingListNotFoundException.class,
                () -> shoppingListService.findById("list-id", user));

        assertEquals("Shopping list not found", exception.getMessage());
    }

    @Test
    void findAllByUserTest() {
        User user = new User("test name", "test@email.com", "123");
        user.setId("id");
        List<ItemDTO> list = Arrays.asList(new ItemDTO("Item 01", "10"));
        ShoppingList shoppingList = new ShoppingList("list name", list, false);
        shoppingList.setUser(user);

        when(shoppingListRepository.findAllByUserId(anyString())).thenReturn(Arrays.asList(shoppingList));

        List<ShoppingList> response = shoppingListService.findAllByUser(user);

        assertEquals(user.getId(), response.get(0).getUser().getId());
    }

    @Test
    void deleteByIdTest() {
        User user = new User("test name", "test@email.com", "123");
        user.setId("id");
        List<ItemDTO> list = Arrays.asList(new ItemDTO("Item 01", "10"));
        ShoppingList shoppingList = new ShoppingList("list name", list, false);
        shoppingList.setId("id");
        shoppingList.setUser(user);

        when(shoppingListRepository.findByIdAndUserId(anyString(), anyString())).thenReturn(Optional.of(shoppingList));

        DefaultResponseDTO response = shoppingListService.deleteById("id", user);

        assertEquals("Shopping list deleted successfully", response.message());

    }

    @Test
    void deleteByIdExceptionTest() {
        User user = new User("test name", "test@email.com", "123");
        user.setId("id");

        when(shoppingListRepository.findByIdAndUserId(anyString(), anyString())).thenReturn(Optional.empty());

        ShoppingListNotFoundException exception = assertThrows(ShoppingListNotFoundException.class, () -> shoppingListService.deleteById("id", user));

        assertEquals("Shopping list not found",exception.getMessage());
    }
}
