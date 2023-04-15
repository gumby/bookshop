package com.polarbookshop.catalogservice.domain;

import com.polarbookshop.catalogservice.config.DataConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
@Import(DataConfig.class)
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE
)
@ActiveProfiles("integration")
public class BookRepositoryJdbcTests {

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private JdbcAggregateTemplate jdbcAggTemplate;

    @Test
    void findAllBooks() {
        var book1 = Book.of("1234561235", "Title", "Author", 12.90);
        var book2 = Book.of("1234561236", "Another title", "Author", 13.90);
        jdbcAggTemplate.insert(book1);
        jdbcAggTemplate.insert(book2);

        Iterable<Book> actualBooks = bookRepo.findAll();

        assertThat(StreamSupport.stream(actualBooks.spliterator(), true)
                .filter(b -> b.isbn().equals(book1.isbn()) || b.isbn().equals(book2.isbn()))
                .collect(Collectors.toList())).hasSize(2);
    }

    @Test
    void findBookByIsbnWhenExisting() {
        var bookIsbn = "1234561237";
        var book = Book.of(bookIsbn, "Title", "Author", 12.90);
        jdbcAggTemplate.insert(book);
        Optional<Book> actualBook = bookRepo.findByIsbn(bookIsbn);

        assertThat(actualBook).isPresent();
        assertThat(actualBook.get().isbn()).isEqualTo(book.isbn());
    }

    @Test
    void findBookByIsbnWhenNotExisting() {
        Optional<Book> actualBook = bookRepo.findByIsbn("1234561238");
        assertThat(actualBook).isEmpty();
    }

    @Test
    void deleteByIsbn() {
        var bookIsbn = "1234561241";
        var bookToCreate = Book.of(bookIsbn, "Title", "Author", 12.90);
        var persistedBook = jdbcAggTemplate.insert(bookToCreate);

        bookRepo.deleteByIsbn(bookIsbn);

        assertThat(jdbcAggTemplate.findById(persistedBook.id(), Book.class)).isNull();
    }
}
