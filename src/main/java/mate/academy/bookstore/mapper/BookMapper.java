package mate.academy.bookstore.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import mate.academy.bookstore.config.MapperConfig;
import mate.academy.bookstore.dto.BookDto;
import mate.academy.bookstore.dto.BookDtoWithoutCategoryIds;
import mate.academy.bookstore.dto.CreateBookRequestDto;
import mate.academy.bookstore.exception.EntityNotFoundException;
import mate.academy.bookstore.model.Book;
import mate.academy.bookstore.model.Category;
import mate.academy.bookstore.repository.book.BookRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface BookMapper {

    @Mapping(target = "categoryIds", ignore = true)
    BookDto toDto(Book book);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    @Mapping(target = "categories", ignore = true)
    Book toBookModel(CreateBookRequestDto bookDto);

    void updateBookFromDto(@MappingTarget Book book, CreateBookRequestDto requestDto);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        if (book.getCategories() != null) {
            Set<Long> categoryIds = book.getCategories().stream()
                    .map(Category::getId)
                    .collect(Collectors.toSet());
            bookDto.setCategoryIds(categoryIds);
        }
    }

    @AfterMapping
    default void setCategories(@MappingTarget Book book, CreateBookRequestDto bookDto) {
        if (bookDto.getCategoryIds() != null && !bookDto.getCategoryIds().isEmpty()) {
            Set<Category> categories = bookDto.getCategoryIds().stream()
                    .map(id -> {
                        Category category = new Category();
                        category.setId(id);
                        return category;
                    })
                    .collect(Collectors.toSet());
            book.setCategories(categories);
        }
    }

    @Named("bookFromId")
    default Book bookFromId(Long id, BookRepository bookRepository) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with id " + id + " not found"));
    }
}
