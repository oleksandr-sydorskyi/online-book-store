package mate.academy.bookstore.dto.cart;

import java.util.List;

public record ShoppingCartDto(
        Long id,
        Long userId,
        List<CartItemResponseDto> cartItems
) {
}
