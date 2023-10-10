package projeto.api.utils.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projeto.api.utils.model.ShoppingList;
import projeto.api.utils.model.User;
import projeto.api.utils.service.ShoppingListService;

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
}
