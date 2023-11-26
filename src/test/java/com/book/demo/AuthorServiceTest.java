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
import static org.mockito.ArgumentMatchers.eq;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.book.demo.models.Author;
import com.book.demo.repositories.AuthorRepository;

public class AuthorServiceTest {
    private AuthorRepository authorRepository;
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUpService() {
        authorRepository = mock(AuthorRepository.class);
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

        Author savedData = authorRepository.save(author);

        when(mongoTemplate.findById(eq("a0a79444-d58b-4bbd-b3cd-d267ee4dca13"), eq(Author.class))).thenReturn(savedData);

        Author data = mongoTemplate.findById(savedData.takeCurrentId(), Author.class);
        
        assertNotNull(data, "ID should be not null, id: %s");
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

        Author savedData = authorRepository.save(author);

        verify(authorRepository).save(author);

        when(mongoTemplate.findOne(any(Query.class), eq(Author.class))).thenReturn(savedData);

        Author authorEmail = mongoTemplate.findOne(new Query(Criteria.where("email").is(author.takeEmail())), Author.class);

        verify(mongoTemplate).findOne(any(Query.class), eq(Author.class));
        
        assertNotNull(authorEmail, "Email should be not null, email: %s");
        assertEquals("johndoe@example.com", authorEmail.takeEmail(), "Email must be 'johndoe@example.com'");
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

        Author saveData = authorRepository.save(author);

        verify(authorRepository).save(author);

        assertAll("Group of author test", 
            () -> assertEquals("a0a79444-d58b-4bbd-b3cd-d267ee4dca13", saveData.takeCurrentId(), "ID must be 'a0a79444-d58b-4bbd-b3cd-d267ee4dca13'"),
            () -> assertEquals("John Doe", saveData.takeFullName(), "Fullname must be 'John Doe'"),
            () -> assertEquals("This is just a test.", saveData.takeBiography(), "Biography must be 'This is just a test.'"),
            () -> assertEquals("johndoe@example.com", saveData.takeEmail(), "Email must be 'johndoe@example.com'"),
            () -> assertEquals("+0217768765", saveData.takePhoneNumber(), "Phone number must be '+0217768765'")
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

        when(mongoTemplate.findById(eq("a0a79444-d58b-4bbd-b3cd-d267ee4dca13"), eq(Author.class))).thenReturn(author);

        Author data = mongoTemplate.findById(author.takeCurrentId(), Author.class); 

        assertNotNull(data, "ID should be not null, id: %s");
        assertEquals("a0a79444-d58b-4bbd-b3cd-d267ee4dca13", data.takeCurrentId(), "ID must be 'a0a79444-d58b-4bbd-b3cd-d267ee4dca13'");

        data.changeFullName("John Smith");
        data.changeBiography("This is just an example test.");
        data.changeEmail("johnsmith78@gmail.com");
        data.changePhoneNumber("+0218976785");
        data.changeCreatedOn(LocalDateTime.of(2023, 11, 26, 7, 32, 54));

        when(authorRepository.save(any(Author.class))).thenReturn(data);

        Author savedUpdateData = authorRepository.save(data);

        verify(authorRepository).save(data);
        
        assertAll("Group of update author test", 
            () -> assertEquals("a0a79444-d58b-4bbd-b3cd-d267ee4dca13", savedUpdateData.takeCurrentId(), "ID must be 'a0a79444-d58b-4bbd-b3cd-d267ee4dca13'"),
            () -> assertEquals("John Smith", savedUpdateData.takeFullName(), "Fullname must be 'John Smith'"),
            () -> assertEquals("This is just an example test.", savedUpdateData.takeBiography(), "Biography must be 'This is just an example test.'"),
            () -> assertEquals("johnsmith78@gmail.com", savedUpdateData.takeEmail(), "Email must be 'johnsmith78@gmail.com'"),
            () -> assertEquals("+0218976785", savedUpdateData.takePhoneNumber(), "Phone number must be '+0218976785'")
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

        when(mongoTemplate.findById(eq("a0a79444-d58b-4bbd-b3cd-d267ee4dca13"), eq(Author.class))).thenReturn(author);
        
        authorRepository.deleteById(author.takeCurrentId());

        verify(authorRepository).deleteById(author.takeCurrentId());

        Author deleteData = mongoTemplate.findById(eq("a0a79444-d58b-4bbd-b3cd-d267ee4dca13"), eq(Author.class));

        assertNull(deleteData, "Author must be null now.");
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
