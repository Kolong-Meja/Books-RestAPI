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

import com.book.demo.dto.publisher.PublisherBooksDTO;
import com.book.demo.models.Author;
import com.book.demo.models.Book;
import com.book.demo.models.Publisher;
import com.book.demo.repositories.AuthorRepository;
import com.book.demo.repositories.BookRepository;
import com.book.demo.repositories.PublisherRepository;

public class PublisherServiceTest {
    private PublisherRepository publisherRepository;
    private AuthorRepository authorRepository;
    private BookRepository bookRepository;
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUpService() {
        publisherRepository = mock(PublisherRepository.class);
        authorRepository = mock(AuthorRepository.class);
        bookRepository = mock(BookRepository.class);
        mongoTemplate = mock(MongoTemplate.class);
    }

    @Test
    void findAllPublishers() {
        Publisher initialPublisherInput = Publisher.getInstance(
            "3865616a-e0ab-497e-85be-4d64b36bc891", 
            "First Publisher", 
            "firstpublisher@example.com", 
            "+0218978786", 
            "This is just an example.", 
            "Road Victory Block A, floor 7th", 
            2011, 
            LocalDateTime.now()
        );

        Publisher continuationPublisherInput = Publisher.getInstance(
            "8ca5c797-16ac-4dcc-bb58-45e2b4bb3cf4", 
            "Second Publisher", 
            "secondpublisher@example.com", 
            "+0217656786", 
            "This is just an example.", 
            "Road Victory Block B, floor 8th", 
            2011, 
            LocalDateTime.now()
        );

        List<Publisher> datas = new ArrayList<Publisher>();
        datas.add(initialPublisherInput);
        datas.add(continuationPublisherInput);

        when(publisherRepository.saveAll(anyList())).thenReturn(datas);

        publisherRepository.saveAll(datas);

        verify(publisherRepository).saveAll(datas);
        
        when(publisherRepository.findAll()).thenReturn(datas);

        List<Publisher> someDatas = publisherRepository.findAll();

        verify(publisherRepository).findAll();

        assertEquals(datas, someDatas, "Datas should be same.");
    }

    @Test
    void findPublisherById() {
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

        when(mongoTemplate.findById(eq("3865616a-e0ab-497e-85be-4d64b36bc891"), eq(Publisher.class))).thenReturn(publisher);

        Publisher data = mongoTemplate.findById(publisher.takeCurrentId(), Publisher.class);

        verify(mongoTemplate).findById(anyString(), eq(Publisher.class));

        assertNotNull(data, "Data cannot be null.");
        assertEquals("3865616a-e0ab-497e-85be-4d64b36bc891", data.takeCurrentId(), "ID must be '3865616a-e0ab-497e-85be-4d64b36bc891'");
    }

    @Test
    void findPublisherName() {
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

        when(mongoTemplate.findOne(any(Query.class), eq(Publisher.class))).thenReturn(publisher);

        Publisher data = mongoTemplate.findOne(new Query(Criteria.where("name").is(publisher.takeName())), Publisher.class);

        verify(mongoTemplate).findOne(any(Query.class), eq(Publisher.class));

        assertNotNull(data, "Data cannot be null.");
        assertEquals("First Publisher", data.takeName(), "Name must be 'First Publisher'");
    }

    @Test
    void findPublisherByEmail() {
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

        when(mongoTemplate.findOne(any(Query.class), eq(Publisher.class))).thenReturn(publisher);

        Publisher data = mongoTemplate.findOne(new Query(Criteria.where("email").is("firstpublisher@example.com")), Publisher.class);

        verify(mongoTemplate).findOne(any(Query.class), eq(Publisher.class));

        assertNotNull(data, "Data cannot be null.");
        assertEquals("firstpublisher@example.com", data.takeEmail(), "Email must be 'firstpublisher@example.com'");
    }

    @Test
    void findPublisherBooks() {
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

        when(mongoTemplate.findOne(any(Query.class), eq(Publisher.class))).thenReturn(publisher);

        Publisher anotherPublisher = mongoTemplate.findOne(new Query(Criteria.where("id").is(book.takePublisher())), Publisher.class);

        verify(mongoTemplate).findOne(any(Query.class), eq(Publisher.class));

        assertNotNull(anotherPublisher, "Data cannot be null.");
        assertEquals(book.takePublisher(), anotherPublisher.takeCurrentId());

        List<Book> books = new ArrayList<>();
        books.add(book);

        when(mongoTemplate.find(any(Query.class), eq(Book.class))).thenReturn(books);

        List<Book> datas = mongoTemplate.find(new Query(Criteria.where("publisherId").is(publisher.takeCurrentId())), Book.class);

        verify(mongoTemplate).find(any(Query.class), eq(Book.class));

        PublisherBooksDTO publisherBooksDTO = new PublisherBooksDTO(
            "3865616a-e0ab-497e-85be-4d64b36bc891", 
            "First Publisher", 
            "firstpublisher@example.com", 
            "+0218978786", 
            "This is just an example.", 
            "Road Victory Block A, floor 7th", 
            2011, 
            datas, 
            LocalDateTime.now()
        );

        assertNotNull(publisherBooksDTO, "Data cannot be null.");
        assertEquals(books, publisherBooksDTO.books(), "Data should be same.");
    }

    @Test
    void createNewPublisher() {
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

        assertNotNull(publisher, "Data cannot be null.");
        assertAll("Group of publisher test", 
            () -> assertEquals("3865616a-e0ab-497e-85be-4d64b36bc891", publisher.takeCurrentId(), "ID must be '3865616a-e0ab-497e-85be-4d64b36bc891'"),
            () -> assertEquals("First Publisher", publisher.takeName(), "Name must be 'First Publisher'"),
            () -> assertEquals("firstpublisher@example.com", publisher.takeEmail(), "Email must be 'firstpublisher@example.com'"),
            () -> assertEquals("+0218978786", publisher.takePhoneNumber(), "Phone number must be '+0218978786'"),
            () -> assertEquals("This is just an example.", publisher.takeBio(), "Bio must be 'This is just an example.'"),
            () -> assertEquals("Road Victory Block A, floor 7th", publisher.takeAddress(), "Address must be 'Road Victory Block A, floor 7th'"),
            () -> assertEquals(2011, publisher.takeFoundYear(), "Found year must be '2011'")
        );
    }

    @Test
    void updateOnePublisher() {
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

        when(mongoTemplate.findById(eq("3865616a-e0ab-497e-85be-4d64b36bc891"), eq(Publisher.class))).thenReturn(publisher);

        Publisher data = mongoTemplate.findById(publisher.takeCurrentId(), Publisher.class);

        verify(mongoTemplate).findById(anyString(), eq(Publisher.class));

        assertNotNull(data, "Data cannot be null.");
        assertEquals("3865616a-e0ab-497e-85be-4d64b36bc891", data.takeCurrentId(), "ID must be '3865616a-e0ab-497e-85be-4d64b36bc891'");

        data.changeName("Second Publisher");
        data.changeEmail("secondpublisher@example.com");
        data.changePhoneNumber("+0218789786");
        data.changeBio("This is just an example brother.");
        data.changeAddress("Road Victory Block A, floor 8th");
        data.changeFoundYear(2012);
        data.changeCreatedOn(LocalDateTime.of(2023, 11, 26, 7, 32, 54));

        when(publisherRepository.save(any(Publisher.class))).thenReturn(data);

        publisherRepository.save(data);

        assertNotNull(data, "Data cannot be null.");
        assertAll("Group of update publisher test", 
            () -> assertEquals("3865616a-e0ab-497e-85be-4d64b36bc891", data.takeCurrentId(), "ID must be '3865616a-e0ab-497e-85be-4d64b36bc891'"),
            () -> assertEquals("Second Publisher", data.takeName(), "Name must be 'Second Publisher'"),
            () -> assertEquals("secondpublisher@example.com", data.takeEmail(), "Email must be 'secondpublisher@example.com'"),
            () -> assertEquals("+0218789786", data.takePhoneNumber(), "Phone number must be '+0218789786'"),
            () -> assertEquals("This is just an example brother.", data.takeBio(), "Bio must be 'This is just an example brother.'"),
            () -> assertEquals("Road Victory Block A, floor 8th", data.takeAddress(), "Address must be 'Road Victory Block A, floor 8th'"),
            () -> assertEquals(2012, data.takeFoundYear(), "Found year must be '2012'")
        );
    }

    @Test
    void removeOnePublisher() {
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

        when(mongoTemplate.findById(eq("3865616a-e0ab-497e-85be-4d64b36bc891"), eq(Publisher.class))).thenReturn(publisher);

        publisherRepository.deleteById(publisher.takeCurrentId());

        verify(publisherRepository).deleteById(publisher.takeCurrentId());

        Publisher deleteData = mongoTemplate.findById(eq("3865616a-e0ab-497e-85be-4d64b36bc891"), eq(Publisher.class));

        assertNull(deleteData, "Publisher data must be null now.");
    }

    @Test
    void removeAllPublishers() {
        Publisher initialPublisherInput = Publisher.getInstance(
            "3865616a-e0ab-497e-85be-4d64b36bc891", 
            "First Publisher", 
            "firstpublisher@example.com", 
            "+0218978786", 
            "This is just an example.", 
            "Road Victory Block A, floor 7th", 
            2011, 
            LocalDateTime.now()
        );

        Publisher continuationPublisherInput = Publisher.getInstance(
            "8ca5c797-16ac-4dcc-bb58-45e2b4bb3cf4", 
            "Second Publisher", 
            "secondpublisher@example.com", 
            "+0217656786", 
            "This is just an example.", 
            "Road Victory Block B, floor 8th", 
            2011, 
            LocalDateTime.now()
        );

        List<Publisher> datas = new ArrayList<Publisher>();
        datas.add(initialPublisherInput);
        datas.add(continuationPublisherInput);

        when(publisherRepository.saveAll(anyList())).thenReturn(datas);

        publisherRepository.saveAll(datas);

        verify(publisherRepository).saveAll(datas);

        publisherRepository.deleteAll();

        verify(publisherRepository).deleteAll();

        List<Publisher> deletedDatas = publisherRepository.findAll();

        assertEquals(new ArrayList<>(), deletedDatas);
    }
}
