package com.book.demo;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.book.demo.repositories.BookRepository;

public class BookServiceTest {
    private BookRepository bookRepository;
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUpService() {
        bookRepository = mock(BookRepository.class);
        mongoTemplate = mock(MongoTemplate.class);
    }

    @Test
    void findAllBooks() {}

    @Test
    void findBookById() {}

    @Test
    void findBookByTitle() {}

    @Test
    void findBookByAuthorId() {}

    @Test
    void findBookByCategory() {}

    @Test
    void createNewBook() {}

    @Test
    void updateOneBook() {}

    @Test
    void removeAllBooks() {}

    @Test
    void removeOneBook() {}
}
