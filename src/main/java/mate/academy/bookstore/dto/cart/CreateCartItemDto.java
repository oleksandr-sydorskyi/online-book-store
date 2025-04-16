package mate.academy.bookstore.dto.cart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateCartItemDto(
        @NotNull
        @Positive
        Long bookId,
        @Positive
        int quantity
) {
}
