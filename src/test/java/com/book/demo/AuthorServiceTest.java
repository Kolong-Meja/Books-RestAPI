package com.book.demo;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.book.demo.dto.author.AuthorBooksDTO;
import com.book.demo.models.Author;
import com.book.demo.models.Book;
import com.book.demo.models.Publisher;
import com.book.demo.repositories.AuthorRepository;
import com.book.demo.repositories.BookRepository;
import com.book.demo.repositories.PublisherRepository;

public class AuthorServiceTest {
    private AuthorRepository authorRepository;
    private PublisherRepository publisherRepository;
    private BookRepository bookRepository;
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUpService() {
        authorRepository = mock(AuthorRepository.class);
        publisherRepository = mock(PublisherRepository.class);
        bookRepository = mock(BookRepository.class);
        mongoTemplate = mock(MongoTemplate.class);
    }

    @Test
    void findAllAuthors() {
        Author initialAuthorInput = Author.getInstance(
            "a0a79444-d58b-4bbd-b3cd-d267ee4dca13",
            "John Doe", 
            "This is just a test.", 
            "johndoe@example.com", 
            "+0217768765",
            LocalDateTime.now()
        );

        Author continuationAuthorInput = Author.getInstance(
            "42a48da5-a706-476c-a4c6-44a960c834d5",
            "John Smith", 
            "This is just a test.", 
            "johnsmith78@example.com", 
            "+0217898767",
            LocalDateTime.now()
        );

        List<Author> datas = new ArrayList<Author>();
        datas.add(initialAuthorInput);
        datas.add(continuationAuthorInput);

        when(authorRepository.findAll()).thenReturn(datas);

        List<Author> someDatas = authorRepository.findAll();

        verify(authorRepository).findAll();

        assertEquals(datas, someDatas, "Datas should be same.");
    }


    @Test
    void findAuthorById() {
        Author author = Author.getInstance(
            "a0a79444-d58b-4bbd-b3cd-d267ee4dca13",
            "John Doe", 
            "This is just a test.", 
            "johndoe@example.com", 
            "+0217768765",
            LocalDateTime.now()
        );
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        authorRepository.save(author);

        verify(authorRepository).save(author);

        when(mongoTemplate.findById(eq("a0a79444-d58b-4bbd-b3cd-d267ee4dca13"), eq(Author.class))).thenReturn(author);

        Author data = mongoTemplate.findById(author.takeCurrentId(), Author.class);
        
        verify(mongoTemplate).findById(anyString(), eq(Author.class));
        
        assertNotNull(data, "Data cannot be null.");
        assertEquals("a0a79444-d58b-4bbd-b3cd-d267ee4dca13", data.takeCurrentId(), "ID must be 'a0a79444-d58b-4bbd-b3cd-d267ee4dca13'");
    }

    @Test
    void findAuthorByName() {
        Author initialAuthorInput = Author.getInstance(
            "a0a79444-d58b-4bbd-b3cd-d267ee4dca13",
            "John Doe", 
            "This is just a test.", 
            "johndoe@example.com", 
            "+0217768765",
            LocalDateTime.now()
        );

        Author continuationAuthorInput = Author.getInstance(
            "42a48da5-a706-476c-a4c6-44a960c834d5",
            "John Doe", 
            "This is just a test.", 
            "johndoe78@example.com", 
            "+0217898767",
            LocalDateTime.now()
        );

        List<Author> datas = new ArrayList<Author>();
        datas.add(initialAuthorInput);
        datas.add(continuationAuthorInput);

        when(authorRepository.saveAll(anyList())).thenReturn(datas);

        authorRepository.saveAll(datas);

        verify(authorRepository).saveAll(datas);
        
        when(mongoTemplate.find(any(Query.class), eq(Author.class))).thenReturn(datas);

        List<Author> authorNames = mongoTemplate.find(new Query(Criteria.where("fullname").is("John Doe")), Author.class);

        verify(mongoTemplate).find(any(Query.class), eq(Author.class));

        assertEquals(datas, authorNames);
    }

    @Test
    void findAuthorByEmail() {
        Author author = Author.getInstance(
            "a0a79444-d58b-4bbd-b3cd-d267ee4dca13",
            "John Doe", 
            "This is just a test.", 
            "johndoe@example.com", 
            "+0217768765",
            LocalDateTime.now()
        );
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        authorRepository.save(author);

        verify(authorRepository).save(author);

        when(mongoTemplate.findOne(any(Query.class), eq(Author.class))).thenReturn(author);

        Author authorEmail = mongoTemplate.findOne(new Query(Criteria.where("email").is(author.takeEmail())), Author.class);

        verify(mongoTemplate).findOne(any(Query.class), eq(Author.class));
        
        assertNotNull(authorEmail, "Email should be not null, email: %s");
        assertEquals("johndoe@example.com", authorEmail.takeEmail(), "Email must be 'johndoe@example.com'");
    }

    @Test
    void findAuthorBooks() {
        Author author = Author.getInstance(
            "a0a79444-d58b-4bbd-b3cd-d267ee4dca13",
            "John Doe", 
            "This is just a test.", 
            "johndoe@example.com", 
            "+0217768765",
            LocalDateTime.now()
        );

        when(authorRepository.save(any(Author.class))).thenReturn(author);

        authorRepository.save(author);

        verify(authorRepository).save(author);

        Publisher publisher = Publisher.getInstance(
            "3865616a-e0ab-497e-85be-4d64b36bc891", 
            "First Publisher", 
            "firstpublisher@example.com", 
            "+0218978786", 
            "This is just an example.", 
            "Road Victory Block A, floor 7th", 
            2011, 
            LocalDateTime.now()
        );

        when(publisherRepository.save(any(Publisher.class))).thenReturn(publisher);

        publisherRepository.save(publisher);

        verify(publisherRepository).save(publisher);

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

        when(mongoTemplate.findOne(any(Query.class), eq(Author.class))).thenReturn(author);

        Author anotherAuthor = mongoTemplate.findOne(new Query(Criteria.where("id").is(book.takeAuthor())), Author.class);

        verify(mongoTemplate).findOne(any(Query.class), eq(Author.class));

        assertNotNull(anotherAuthor, "Data cannot be null.");
        assertEquals(book.takeAuthor(), anotherAuthor.takeCurrentId(), "Author ID should be same, 'a0a79444-d58b-4bbd-b3cd-d267ee4dca13'");

        List<Book> books = new ArrayList<>();
        books.add(book);
        
        when(mongoTemplate.find(any(Query.class), eq(Book.class))).thenReturn(books);

        List<Book> datas = mongoTemplate.find(new Query(Criteria.where("authorId").is(anotherAuthor.takeCurrentId())), Book.class);

        verify(mongoTemplate).find(any(Query.class), eq(Book.class));

        AuthorBooksDTO authorBooksDTO = new AuthorBooksDTO(
            "a0a79444-d58b-4bbd-b3cd-d267ee4dca13",
            "John Doe", 
            "This is just a test.", 
            "johndoe@example.com", 
            "+0217768765", 
            datas, 
            LocalDateTime.now()
        );

        assertNotNull(authorBooksDTO, "Data cannot be null.");
        assertEquals(books, authorBooksDTO.books(), "Data should be same.");
    }

    @Test
    void createNewAuthor() {
        Author author = Author.getInstance(
            "a0a79444-d58b-4bbd-b3cd-d267ee4dca13",
            "John Doe", 
            "This is just a test.", 
            "johndoe@example.com", 
            "+0217768765",
            LocalDateTime.now()
        );
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        authorRepository.save(author);

        verify(authorRepository).save(author);

        assertNotNull(author, "Data cannot be null.");
        assertAll("Group of author test", 
            () -> assertEquals("a0a79444-d58b-4bbd-b3cd-d267ee4dca13", author.takeCurrentId(), "ID must be 'a0a79444-d58b-4bbd-b3cd-d267ee4dca13'"),
            () -> assertEquals("John Doe", author.takeFullName(), "Fullname must be 'John Doe'"),
            () -> assertEquals("This is just a test.", author.takeBiography(), "Biography must be 'This is just a test.'"),
            () -> assertEquals("johndoe@example.com", author.takeEmail(), "Email must be 'johndoe@example.com'"),
            () -> assertEquals("+0217768765", author.takePhoneNumber(), "Phone number must be '+0217768765'")
        );
    }

    @Test
    void updateOneAuthor() {
        Author author = Author.getInstance(
            "a0a79444-d58b-4bbd-b3cd-d267ee4dca13",
            "John Doe", 
            "This is just a test.", 
            "johndoe@example.com", 
            "+0217768765",
            LocalDateTime.now()
        );
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        authorRepository.save(author);
        
        when(mongoTemplate.findById(eq("a0a79444-d58b-4bbd-b3cd-d267ee4dca13"), eq(Author.class))).thenReturn(author);

        Author data = mongoTemplate.findById(author.takeCurrentId(), Author.class); 

        verify(mongoTemplate).findById(anyString(), eq(Author.class));

        assertNotNull(data, "Data cannot be null.");
        assertEquals("a0a79444-d58b-4bbd-b3cd-d267ee4dca13", data.takeCurrentId(), "ID must be 'a0a79444-d58b-4bbd-b3cd-d267ee4dca13'");

        data.changeFullName("John Smith");
        data.changeBiography("This is just an example test.");
        data.changeEmail("johnsmith78@gmail.com");
        data.changePhoneNumber("+0218976785");
        data.changeCreatedOn(LocalDateTime.of(2023, 11, 26, 7, 32, 54));

        when(authorRepository.save(any(Author.class))).thenReturn(data);

        authorRepository.save(data);
        
        assertNotNull(data, "Data cannot be null.");
        assertAll("Group of update author test", 
            () -> assertEquals("a0a79444-d58b-4bbd-b3cd-d267ee4dca13", data.takeCurrentId(), "ID must be 'a0a79444-d58b-4bbd-b3cd-d267ee4dca13'"),
            () -> assertEquals("John Smith", data.takeFullName(), "Fullname must be 'John Smith'"),
            () -> assertEquals("This is just an example test.", data.takeBiography(), "Biography must be 'This is just an example test.'"),
            () -> assertEquals("johnsmith78@gmail.com", data.takeEmail(), "Email must be 'johnsmith78@gmail.com'"),
            () -> assertEquals("+0218976785", data.takePhoneNumber(), "Phone number must be '+0218976785'")
        );
    }

    @Test
    void removeOneAuthor() {
        Author author = Author.getInstance(
            "a0a79444-d58b-4bbd-b3cd-d267ee4dca13",
            "John Doe", 
            "This is just a test.", 
            "johndoe@example.com", 
            "+0217768765",
            LocalDateTime.now()
        );
        
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        authorRepository.save(author);

        verify(authorRepository).save(author);
        
        authorRepository.deleteById(author.takeCurrentId());

        verify(authorRepository).deleteById(author.takeCurrentId());

        Author deleteData = mongoTemplate.findById(eq("a0a79444-d58b-4bbd-b3cd-d267ee4dca13"), eq(Author.class));

        assertNull(deleteData, "Author data should be null now.");
    }

    @Test
    void removeAllAuthors() {
        Author initialAuthorInput = Author.getInstance(
             "a0a79444-d58b-4bbd-b3cd-d267ee4dca13",
            "John Doe", 
            "This is just a test.", 
            "johndoe@example.com", 
            "+0217768765",
            LocalDateTime.now()
        );

        Author continuationAuthorInput = Author.getInstance(
            "42a48da5-a706-476c-a4c6-44a960c834d5",
            "John Smith", 
            "This is just a test.", 
            "johnsmith78@example.com", 
            "+0217898767",
            LocalDateTime.now()
        );

        List<Author> datas = new ArrayList<Author>();
        datas.add(initialAuthorInput);
        datas.add(continuationAuthorInput);

        when(authorRepository.saveAll(anyList())).thenReturn(datas);

        authorRepository.saveAll(datas);

        verify(authorRepository).saveAll(datas);

        authorRepository.deleteAll();

        verify(authorRepository).deleteAll();

        List<Author> deletedDatas = authorRepository.findAll();

        assertEquals(new ArrayList<>(), deletedDatas);
    }
}
