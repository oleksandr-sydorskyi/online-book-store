package mate.academy.bookstore.repository.cart;

import java.util.Optional;
import mate.academy.bookstore.model.CartItem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @EntityGraph(attributePaths = {"book"})
    @Query("SELECT ci FROM CartItem ci JOIN ci.shoppingCart sc "
            + "WHERE sc.user.id = :userId AND ci.id = :cartItemId")
    Optional<CartItem> findCartItemByUserIdAndCartItemId(
            @Param("userId") Long userId, @Param("cartItemId") Long cartItemId);

    Optional<CartItem> findByShoppingCartIdAndBookId(Long shoppingCartId, Long bookId);
}
