package mate.academy.bookstore.service;

import mate.academy.bookstore.dto.cart.CartItemRequestDto;
import mate.academy.bookstore.dto.cart.CartItemResponseDto;
import mate.academy.bookstore.dto.cart.CreateCartItemDto;
import mate.academy.bookstore.dto.cart.ShoppingCartDto;
import mate.academy.bookstore.model.User;

public interface ShoppingCartService {
    ShoppingCartDto getShoppingCart();

    ShoppingCartDto addBookToShoppingCart(CreateCartItemDto requestDto);

    CartItemResponseDto updateQuantity(Long cartItemId, CartItemRequestDto requestDto);

    void removeBookFromShoppingCart(Long cartItemId);

    void createNewShoppingCart(User user);
}
