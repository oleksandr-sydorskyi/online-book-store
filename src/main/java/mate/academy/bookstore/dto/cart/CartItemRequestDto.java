package mate.academy.bookstore.dto.cart;

import jakarta.validation.constraints.Positive;

public record CartItemRequestDto(
        @Positive
        int quantity
) {
}
