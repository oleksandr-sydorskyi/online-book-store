package mate.academy.bookstore.repository.book;

import java.util.Optional;
import mate.academy.bookstore.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    @EntityGraph(attributePaths = "categories")
    Page<Book> findByCategoriesId(Long categoryId, Pageable pageable);

    @EntityGraph(attributePaths = "categories")
    Optional<Book> findById(Long id);
}
