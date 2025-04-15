package mate.academy.bookstore.service;

import mate.academy.bookstore.dto.BookDto;
import mate.academy.bookstore.dto.BookDtoWithoutCategoryIds;
import mate.academy.bookstore.dto.BookSearchParametersDto;
import mate.academy.bookstore.dto.CreateBookRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    BookDto addBook(CreateBookRequestDto requestDto);

    Page<BookDto> getAllBooks(Pageable pageable);

    BookDto getBookById(Long id);

    BookDto update(CreateBookRequestDto requestDto, Long id);

    void deleteById(Long id);

    Page<BookDto> searchBooks(BookSearchParametersDto params, Pageable pageable);

    Page<BookDtoWithoutCategoryIds> findAllByCategoryId(Long categoryId, Pageable pageable);
}
