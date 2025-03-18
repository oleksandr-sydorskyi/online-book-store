package mate.academy.bookstore.service;

import java.util.List;
import mate.academy.bookstore.dto.BookDto;
import mate.academy.bookstore.dto.CreateBookRequestDto;

public interface BookService {

    BookDto addBook(CreateBookRequestDto requestDto);

    List<BookDto> getAllBooks();

    BookDto getBookById(Long id);

    List<BookDto> getAllBooksByAuthor(String author);
}
