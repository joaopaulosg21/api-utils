package projeto.api.utils.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import projeto.api.utils.model.ShoppingList;
import projeto.api.utils.model.User;
import projeto.api.utils.service.ShoppingListService;

import java.util.List;

@RestController
@RequestMapping("/api/list/")
@RequiredArgsConstructor
public class ShoppingListController {
    private final ShoppingListService shoppingListService;

    @PostMapping()
    public ResponseEntity<ShoppingList> create(@AuthenticationPrincipal User user,
                                               @Valid @RequestBody ShoppingList shoppingList) {
        return ResponseEntity.status(HttpStatus.CREATED).body(shoppingListService.create(user,shoppingList));
    }

    @GetMapping("find/public")
    public ResponseEntity<List<ShoppingList>> findAllPublic() {
        return ResponseEntity.ok(shoppingListService.findAllPublic());
    }

    @GetMapping("find/private")
    public ResponseEntity<List<ShoppingList>> findAllPrivate(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(shoppingListService.findAllPrivate(user));
    }

    @GetMapping("find/{id}")
    public ResponseEntity<ShoppingList> findById(@AuthenticationPrincipal User user, @PathVariable String id) {
        return ResponseEntity.ok(shoppingListService.findById(id,user));
    }

    @GetMapping("find/all")
    public ResponseEntity<List<ShoppingList>> findALlByUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(shoppingListService.findAllByUser(user));
    }
}
