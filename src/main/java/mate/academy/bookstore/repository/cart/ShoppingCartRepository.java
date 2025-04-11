package mate.academy.bookstore.repository.cart;

import java.util.Optional;
import mate.academy.bookstore.model.CartItem;
import mate.academy.bookstore.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    @Query("SELECT ci FROM CartItem ci "
            + "JOIN FETCH ci.book b "
            + "JOIN ci.shoppingCart sc "
            + "JOIN sc.user u "
            + "WHERE u.id = :userId AND ci.id = :id"
    )
    Optional<CartItem> cartItemsByUserIdAndItemId(Long userId, Long id);

    Optional<ShoppingCart> findByUserId(Long userId);
}
