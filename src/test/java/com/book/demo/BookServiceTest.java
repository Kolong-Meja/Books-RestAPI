package com.book.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.book.demo.models.Book;
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
    void findAllBooks() {
        Book initialBookInput = Book.getInstance(
            "0f7a94c6-3dd1-438f-84b3-54b032d3f151", 
            1506603733, 
            "Example Book", 
            "This is just an example.", 
            "a0a79444-d58b-4bbd-b3cd-d267ee4dca13", 
            "3865616a-e0ab-497e-85be-4d64b36bc891", 
            2012, 
            532, 
            12.99, 
            "Adventure, Horror", 
            LocalDateTime.now()
        );

        Book continuationBookInput = Book.getInstance(
            "9ac6fa59-8215-4100-856e-462412f0f5f0", 
            1506603734, 
            "Example Book Second", 
            "This is just an example.", 
            "42a48da5-a706-476c-a4c6-44a960c834d5", 
            "8ca5c797-16ac-4dcc-bb58-45e2b4bb3cf4", 
            2013, 
            556, 
            14.99, 
            "Adventure, Drama", 
            LocalDateTime.now()
        );

        List<Book> books = new ArrayList<>();
        books.add(initialBookInput);
        books.add(continuationBookInput);

        when(bookRepository.saveAll(anyList())).thenReturn(books);

        bookRepository.saveAll(books);

        verify(bookRepository).saveAll(books);

        assertNotNull(books, "Data's cannot be null.");

        when(bookRepository.findAll()).thenReturn(books);

        List<Book> datas = bookRepository.findAll();

        verify(bookRepository).findAll();

        assertNotNull(datas, "Data's cannot be null.");
        assertEquals(books, datas, "Data's must be same.");
    }

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
