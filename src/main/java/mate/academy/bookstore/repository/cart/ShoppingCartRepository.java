package mate.academy.bookstore.repository.cart;

import java.util.Optional;
import mate.academy.bookstore.model.CartItem;
import mate.academy.bookstore.model.ShoppingCart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    @Query("SELECT ci FROM ShoppingCart sc JOIN sc.cartItems ci "
            + "WHERE sc.user.id = :userId AND ci.id = :cartItemId")
    @EntityGraph(value = "CartItem.book.shoppingCart.user", type = EntityGraph.EntityGraphType.LOAD)
    Optional<CartItem> findCartItemByUserIdAndCartItemId(
            @Param("userId") Long userId, @Param("cartItemId") Long cartItemId);

    Optional<ShoppingCart> findByUserId(Long userId);
}
