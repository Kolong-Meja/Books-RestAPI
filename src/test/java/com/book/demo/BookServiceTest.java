package com.book.demo;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

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
    void findBookById() {
        Book book = Book.getInstance(
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

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        bookRepository.save(book);

        verify(bookRepository).save(book);

        when(mongoTemplate.findById(eq("0f7a94c6-3dd1-438f-84b3-54b032d3f151"), eq(Book.class))).thenReturn(book);

        Book data = mongoTemplate.findById(book.takeCurrentId(), Book.class);

        verify(mongoTemplate).findById(anyString(), eq(Book.class));

        assertNotNull(data, "Data cannot be null.");
        assertEquals("0f7a94c6-3dd1-438f-84b3-54b032d3f151", data.takeCurrentId(), "ID must be '0f7a94c6-3dd1-438f-84b3-54b032d3f151'");
    }

    @Test
    void findBookByTitle() {
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
            "Example Book", 
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

        when(mongoTemplate.find(any(Query.class), eq(Book.class))).thenReturn(books);

        List<Book> datas = mongoTemplate.find(new Query(Criteria.where("title").is("Example Book")), Book.class);
        
        verify(mongoTemplate).find(any(Query.class), eq(Book.class));

        assertEquals(books, datas);
    }

    @Test
    void findBookByAuthorId() {
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
            "a0a79444-d58b-4bbd-b3cd-d267ee4dca13", 
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

        when(mongoTemplate.find(any(Query.class), eq(Book.class))).thenReturn(books);

        List<Book> datas = mongoTemplate.find(new Query(Criteria.where("authorId").is("a0a79444-d58b-4bbd-b3cd-d267ee4dca13")), Book.class);

        verify(mongoTemplate).find(any(Query.class), eq(Book.class));

        assertEquals(books, datas);
    }

    @Test
    void createNewBook() {
        Book book = Book.getInstance(
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

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        bookRepository.save(book);

        verify(bookRepository).save(book);

        assertNotNull(book, "Data cannot be null.");
        assertAll("Group of book test", 
            () -> assertEquals("0f7a94c6-3dd1-438f-84b3-54b032d3f151", book.takeCurrentId(), "ID must be '0f7a94c6-3dd1-438f-84b3-54b032d3f151'"),
            () -> assertEquals(1506603733, book.takeCurrentISBN(), "ISBN must be '1506603733'"),
            () -> assertEquals("Example Book", book.takeBookTitle(), "Title must be 'Example Book'"),
            () -> assertEquals("This is just an example.", book.takeBookSynopsis(), "Synopsis must be 'This is just an example.'"),
            () -> assertEquals("a0a79444-d58b-4bbd-b3cd-d267ee4dca13", book.takeAuthor(), "Author ID must be 'a0a79444-d58b-4bbd-b3cd-d267ee4dca13'"),
            () -> assertEquals("3865616a-e0ab-497e-85be-4d64b36bc891", book.takePublisher(), "Publisher ID must be '3865616a-e0ab-497e-85be-4d64b36bc891'"),
            () -> assertEquals(2012, book.takeBookPublishYear(), "Publish year must be '2012'"),
            () -> assertEquals(532, book.takeBookTotalPage(), "Total page must be '532'"),
            () -> assertEquals(12.99, book.takeBookPrice(), "Price must be '12.99'"),
            () -> assertEquals("Adventure, Horror", book.takeBookCategory(), "Category must be 'Adventure, Horror'")
        );
    }

    @Test
    void updateOneBook() {
        Book book = Book.getInstance(
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

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        bookRepository.save(book);
        
        when(mongoTemplate.findById(eq("0f7a94c6-3dd1-438f-84b3-54b032d3f151"), eq(Book.class))).thenReturn(book);

        Book data = mongoTemplate.findById(book.takeCurrentId(), Book.class);

        verify(mongoTemplate).findById(anyString(), eq(Book.class));

        assertNotNull(data, "Data cannot be null.");
        assertEquals("0f7a94c6-3dd1-438f-84b3-54b032d3f151", data.takeCurrentId(), "ID must be '0f7a94c6-3dd1-438f-84b3-54b032d3f151'");

        data.changeISBN(1506603744);
        data.changeTitle("Example of Book");
        data.changeSynopsis("This is just another example.");
        data.changeAuthor("a0a79444-d58b-4bbd-b3cd-d267ee4dca13");
        data.changePublisher("3865616a-e0ab-497e-85be-4d64b36bc891");
        data.changePublishYear(2013);
        data.changeTotalPage(550);
        data.changePrice(14.99);
        data.changeCategory("Adventure, Drama");
        data.changeCreatedOn(LocalDateTime.of(2023, 11, 26, 7, 32, 54));

        when(bookRepository.save(any(Book.class))).thenReturn(data);

        bookRepository.save(data);

        assertNotNull(data, "Data cannot be null.");
        assertAll("Group of update book test", 
            () -> assertEquals("0f7a94c6-3dd1-438f-84b3-54b032d3f151", book.takeCurrentId(), "ID must be '0f7a94c6-3dd1-438f-84b3-54b032d3f151'"),
            () -> assertEquals(1506603744, book.takeCurrentISBN(), "ISBN must be '1506603733'"),
            () -> assertEquals("Example of Book", book.takeBookTitle(), "Title must be 'Example Book'"),
            () -> assertEquals("This is just another example.", book.takeBookSynopsis(), "Synopsis must be 'This is just an example.'"),
            () -> assertEquals("a0a79444-d58b-4bbd-b3cd-d267ee4dca13", book.takeAuthor(), "Author ID must be 'a0a79444-d58b-4bbd-b3cd-d267ee4dca13'"),
            () -> assertEquals("3865616a-e0ab-497e-85be-4d64b36bc891", book.takePublisher(), "Publisher ID must be '3865616a-e0ab-497e-85be-4d64b36bc891'"),
            () -> assertEquals(2013, book.takeBookPublishYear(), "Publish year must be '2012'"),
            () -> assertEquals(550, book.takeBookTotalPage(), "Total page must be '532'"),
            () -> assertEquals(14.99, book.takeBookPrice(), "Price must be '12.99'"),
            () -> assertEquals("Adventure, Drama", book.takeBookCategory(), "Category must be 'Adventure, Horror'")
        ); 
    }

    @Test
    void removeAllBooks() {
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
            "a0a79444-d58b-4bbd-b3cd-d267ee4dca13", 
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

        bookRepository.deleteAll();

        verify(bookRepository).deleteAll();

        List<Book> datas = bookRepository.findAll();

        assertEquals(new ArrayList<>(), datas);
    }

    @Test
    void removeOneBook() {
        Book book = Book.getInstance(
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

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        bookRepository.save(book);

        verify(bookRepository).save(book);

        bookRepository.deleteById(book.takeCurrentId());

        verify(bookRepository).deleteById(book.takeCurrentId());

        Book data = mongoTemplate.findById(eq("0f7a94c6-3dd1-438f-84b3-54b032d3f151"), eq(Book.class));

        assertNull(data, "Book data should be null now.");
    }
}
