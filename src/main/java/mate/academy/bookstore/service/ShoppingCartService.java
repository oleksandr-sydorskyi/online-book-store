package mate.academy.bookstore.service;

import mate.academy.bookstore.dto.cart.CreateCartItemDto;
import mate.academy.bookstore.dto.cart.ShoppingCartDto;
import mate.academy.bookstore.dto.cart.UpdateCartItemDto;
import mate.academy.bookstore.model.User;

public interface ShoppingCartService {
    ShoppingCartDto getShoppingCart(Long userId);

    ShoppingCartDto addBookToShoppingCart(Long userId, CreateCartItemDto requestDto);

    ShoppingCartDto updateQuantity(Long userId, Long cartItemId, UpdateCartItemDto requestDto);

    void removeBookFromShoppingCart(Long userId, Long cartItemId);

    void createNewShoppingCart(User user);
}
