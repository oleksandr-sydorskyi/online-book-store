package mate.academy.bookstore;

import java.math.BigDecimal;
import mate.academy.bookstore.model.Book;
import mate.academy.bookstore.service.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OnlineBookStoreApplication {
    private final BookService bookService;

    public OnlineBookStoreApplication(BookService bookService) {
        this.bookService = bookService;
    }

    public static void main(String[] args) {
        SpringApplication.run(OnlineBookStoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner init() {
        return args -> {
            Book book = new Book();
            book.setTitle("Book Title");
            book.setAuthor("Author");
            book.setIsbn("1234567");
            book.setPrice(new BigDecimal("29.99"));
            book.setDescription("Description");
            book.setCoverImage("cover.jpg");

            bookService.save(book);
            bookService.findAll().forEach(System.out::println);
        };
    }
}
