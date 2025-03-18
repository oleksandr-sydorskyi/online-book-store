package mate.academy.bookstore.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mate.academy.bookstore.exception.DataProcessingException;
import mate.academy.bookstore.model.Book;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {
    private final EntityManagerFactory factory;

    @Override
    public Book addBook(Book book) {
        EntityTransaction transaction = null;
        try (EntityManager manager = factory.createEntityManager()) {
            transaction = manager.getTransaction();
            transaction.begin();
            Book existingBook = getBookByIsbn(manager, book.getIsbn());
            if (existingBook != null) {
                return existingBook;
            }
            if (book.getId() == null) {
                manager.persist(book);
            } else {
                book = manager.merge(book);
            }
            transaction.commit();
            return book;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new DataProcessingException("Failed to save book: " + book, e);
        }
    }

    private Book getBookByIsbn(EntityManager manager, String isbn) {
        return manager
                .createQuery("SELECT b FROM Book b WHERE b.isbn = :isbn", Book.class)
                .setParameter("isbn", isbn)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Book> getAllBooks() {
        try (EntityManager manager = factory.createEntityManager()) {
            return manager.createQuery("SELECT b FROM Book b", Book.class).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Error retrieving books", e);
        }
    }

    @Override
    public Optional<Book> getBookById(Long id) {
        try (EntityManager manager = factory.createEntityManager()) {
            Book book = manager.find(Book.class, id);
            return Optional.ofNullable(book);
        } catch (Exception e) {
            throw new DataProcessingException("Error retrieving book with id: " + id, e);
        }
    }

    @Override
    public List<Book> getAllBooksByAuthor(String author) {
        String lowerCaseAuthor = author.toLowerCase();
        try (EntityManager manager = factory.createEntityManager()) {
            String query = "SELECT b FROM Book b WHERE lower(b.author) LIKE :author";
            return manager.createQuery(query, Book.class)
                    .setParameter("author", "%" + lowerCaseAuthor + "%")
                    .getResultList();
        }
    }
}
