package mate.academy.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstore.dto.cart.CartItemRequestDto;
import mate.academy.bookstore.dto.cart.CartItemResponseDto;
import mate.academy.bookstore.dto.cart.CreateCartItemDto;
import mate.academy.bookstore.dto.cart.ShoppingCartDto;
import mate.academy.bookstore.service.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shopping Cart Management", description = "Endpoints for managing user's shopping cart")
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_USER')")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @Operation(summary = "Get user's shopping cart")
    @GetMapping
    public ShoppingCartDto getShoppingCart() {
        return shoppingCartService.getShoppingCart();
    }

    @Operation(summary = "Add a book to the shopping cart")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingCartDto addBookToShoppingCart(
            @RequestBody @Valid CreateCartItemDto requestDto) {
        return shoppingCartService.addBookToShoppingCart(requestDto);
    }

    @Operation(summary = "Update quantity of a book in the shopping cart")
    @PutMapping("/items/{cartItemId}")
    public CartItemResponseDto updateQuantity(@PathVariable Long cartItemId,
                                              @RequestBody @Valid CartItemRequestDto requestDto) {
        return shoppingCartService.updateQuantity(cartItemId, requestDto);
    }

    @Operation(summary = "Remove a book from the shopping cart")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/items/{cartItemId}")
    public void removeBookFromShoppingCart(@PathVariable Long cartItemId) {
        shoppingCartService.removeBookFromShoppingCart(cartItemId);
    }
}
