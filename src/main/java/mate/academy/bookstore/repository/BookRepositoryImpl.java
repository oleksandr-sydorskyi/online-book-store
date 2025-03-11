package mate.academy.bookstore.repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstore.model.Book;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {

    private final EntityManager entityManager;

    @Override
    @Transactional
    public Book save(Book book) {
        try {
            if (book.getId() == null) {
                entityManager.persist(book);
                return book;
            } else {
                return entityManager.merge(book);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error saving book", e);
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
