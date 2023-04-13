package com.polarbookshop.catalogservice.domain;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String isbn) {
        super(String.format("the book with ISBN %s was not found", isbn));
    }
}
