package mate.academy.bookstore;

import java.math.BigDecimal;
import mate.academy.bookstore.model.Book;
import mate.academy.bookstore.service.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OnlineBookStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineBookStoreApplication.class, args);
    }

    CommandLineRunner init(BookService bookService) {
        return args -> bookService.save(new Book(null, "Book Title",
                "Author", "123456789", new BigDecimal("29.99"),
                "Description", "cover.jpg"));
    }
}
