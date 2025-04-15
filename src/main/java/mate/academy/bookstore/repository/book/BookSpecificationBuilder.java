package mate.academy.bookstore.repository.book;

import lombok.RequiredArgsConstructor;
import mate.academy.bookstore.dto.BookSearchParametersDto;
import mate.academy.bookstore.model.Book;
import mate.academy.bookstore.repository.SpecificationBuilder;
import mate.academy.bookstore.repository.SpecificationProviderManager;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private static final String TITLE = "title";
    private static final String AUTHOR = "author";
    private static final String ISBN = "isbn";
    private final SpecificationProviderManager<Book> bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParametersDto searchParameters) {
        Specification<Book> spec = Specification.where(null);
        if (searchParameters.title() != null) {
            spec = spec.and(getSpecification(TITLE, searchParameters.title()));
        }
        if (searchParameters.author() != null) {
            spec = spec.and(getSpecification(AUTHOR, searchParameters.author()));
        }
        if (searchParameters.isbn() != null) {
            spec = spec.and(getSpecification(ISBN, searchParameters.isbn()));
        }
        return spec;
    }

    private Specification<Book> getSpecification(String key, String value) {
        return bookSpecificationProviderManager.getSpecificationProvider(key)
                .getSpecification(value);
    }
}
