package mate.academy.bookstore.dto.cart;

import jakarta.validation.constraints.Positive;

public record UpdateCartItemDto(
        @Positive
        int quantity
) {
}
