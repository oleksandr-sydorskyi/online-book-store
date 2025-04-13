package mate.academy.bookstore.service;

import mate.academy.bookstore.dto.cart.CreateCartItemDto;
import mate.academy.bookstore.dto.cart.ShoppingCartDto;
import mate.academy.bookstore.dto.cart.UpdateCartItemDto;
import mate.academy.bookstore.model.User;

public interface ShoppingCartService {
    ShoppingCartDto getShoppingCart();

    ShoppingCartDto addBookToShoppingCart(CreateCartItemDto requestDto);

    ShoppingCartDto updateQuantity(Long cartItemId, UpdateCartItemDto requestDto);

    void removeBookFromShoppingCart(Long cartItemId);

    void createNewShoppingCart(User user);
}
