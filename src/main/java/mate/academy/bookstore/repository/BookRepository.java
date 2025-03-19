package mate.academy.bookstore.repository;

import java.util.List;
import java.util.Optional;
import mate.academy.bookstore.model.Book;

public interface BookRepository {

    Book addBook(Book book);

    List<Book> getAllBooks();

    Optional<Book> getBookById(Long id);
}

