package mate.academy.bookstore.service;

import mate.academy.bookstore.dto.AddToCartRequestDto;
import mate.academy.bookstore.dto.ShoppingCartDto;
import mate.academy.bookstore.dto.UpdateCartItemRequestDto;

public interface ShoppingCartService {
    ShoppingCartDto getShoppingCart();

    ShoppingCartDto addBookToShoppingCart(AddToCartRequestDto requestDto);

    ShoppingCartDto updateQuantity(Long cartItemId, UpdateCartItemRequestDto requestDto);

    ShoppingCartDto removeBookFromShoppingCart(Long cartItemId);
}
