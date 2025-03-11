package mate.academy.bookstore.repository;

import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstore.model.Book;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {
    private final EntityManager entityManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Book save(Book book) {
        try {
            if (book.getId() == null) {
                entityManager.persist(book);
            } else {
                book = entityManager.merge(book);
            }
            return book;
        } catch (Exception e) {
            throw new RuntimeException("Error saving book: " + book, e);
        }
    }

    @Override
    public List<Book> findAll() {
        try {
            return entityManager.createQuery("SELECT b FROM Book b", Book.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving books", e);
        }
    }
}
