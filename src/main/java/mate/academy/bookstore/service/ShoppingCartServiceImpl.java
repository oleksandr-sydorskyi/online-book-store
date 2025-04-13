package mate.academy.bookstore.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstore.dto.cart.CreateCartItemDto;
import mate.academy.bookstore.dto.cart.ShoppingCartDto;
import mate.academy.bookstore.dto.cart.UpdateCartItemDto;
import mate.academy.bookstore.exception.EntityNotFoundException;
import mate.academy.bookstore.mapper.ShoppingCartMapper;
import mate.academy.bookstore.model.Book;
import mate.academy.bookstore.model.CartItem;
import mate.academy.bookstore.model.ShoppingCart;
import mate.academy.bookstore.model.User;
import mate.academy.bookstore.repository.book.BookRepository;
import mate.academy.bookstore.repository.cart.CartItemRepository;
import mate.academy.bookstore.repository.cart.ShoppingCartRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final ShoppingCartMapper shoppingCartMapper;

    @Override
    public ShoppingCartDto getShoppingCart() {
        Long userId = getCurrentUserId();
        return shoppingCartRepository.findByUserId(userId)
                .map(shoppingCartMapper::toShoppingCartDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Shopping cart not found for user id: " + userId));
    }

    @Override
    public ShoppingCartDto addBookToShoppingCart(CreateCartItemDto requestDto) {
        Long userId = getCurrentUserId();
        Book book = bookRepository.findById(requestDto.bookId())
                .orElseThrow(() -> new EntityNotFoundException("Can't find book with id: "
                        + requestDto.bookId()));
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Shopping cart not found "
                        + "for user id: " + userId));
        Optional<CartItem> existingCartItem = cartItemRepository
                .findByShoppingCartIdAndBookId(shoppingCart.getId(), book.getId());
        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + requestDto.quantity());
        } else {
            CartItem cartItem = shoppingCartMapper.toModel(requestDto);
            cartItem.setShoppingCart(shoppingCart);
            cartItem.setBook(book);
            shoppingCart.getCartItems().add(cartItem);
        }
        shoppingCartRepository.save(shoppingCart);
        return shoppingCartMapper.toShoppingCartDto(shoppingCart);
    }

    @Override
    public ShoppingCartDto updateQuantity(Long cartItemId, UpdateCartItemDto requestDto) {
        Long userId = getCurrentUserId();
        CartItem cartItem = shoppingCartRepository.cartItemsByUserIdAndItemId(userId, cartItemId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find cart item with id: "
                        + cartItemId + " in your cart"));
        cartItem.setQuantity(requestDto.quantity());
        cartItemRepository.save(cartItem);
        ShoppingCart shoppingCart = cartItem.getShoppingCart();
        return shoppingCartMapper.toShoppingCartDto(shoppingCart);
    }

    @Override
    public void removeBookFromShoppingCart(Long cartItemId) {
        Long userId = getCurrentUserId();
        Optional<CartItem> cartItem = shoppingCartRepository
                .cartItemsByUserIdAndItemId(userId, cartItemId);
        if (cartItem.isPresent()) {
            CartItem item = cartItem.get();
            cartItemRepository.delete(item);
        } else {
            throw new EntityNotFoundException("Can't find cart item with id: "
                    + cartItemId + " in your cart");
        }
    }

    @Override
    public void createNewShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user.getId();
    }
}
