package mate.academy.bookstore.repository.book.spec;

import mate.academy.bookstore.model.Book;
import mate.academy.bookstore.repository.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class IsbnSpecificationProvider implements SpecificationProvider<Book> {
    private static final String ISBN = "isbn";

    @Override
    public String getKey() {
        return ISBN;
    }

    public Specification<Book> getSpecification(String param) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get(ISBN)),
                        "%" + param.toLowerCase() + "%");
    }
}
