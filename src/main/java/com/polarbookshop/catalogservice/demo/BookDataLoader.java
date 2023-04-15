package com.polarbookshop.catalogservice.demo;

import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.domain.BookRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Profile("testdata")
public class BookDataLoader {

    private final BookRepository bookRepo;

    public BookDataLoader(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadBookTestData() {
        var book1 = Book.of("1234567891", "Northern Lights", "Lyra Silverstart", 9.90);
        var book2 = Book.of("1234567892", "Polar Journey", "Iorek Polarson", 12.90);
        bookRepo.save(book1);
        bookRepo.save(book2);
    }
}
