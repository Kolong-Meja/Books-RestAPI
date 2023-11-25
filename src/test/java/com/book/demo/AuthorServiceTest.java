package com.book.demo;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.book.demo.models.Author;
import com.book.demo.repositories.AuthorRepository;
import com.book.demo.services.AuthorService;

public class AuthorServiceTest {
    private AuthorRepository authorRepository;
    private MongoTemplate mongoTemplate;
    private AuthorService authorService;

    @BeforeEach
    void setUpService() {
        authorRepository = mock(AuthorRepository.class);
        mongoTemplate = mock(MongoTemplate.class);
        authorService = mock(AuthorService.class);
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

        Author authorId = mongoTemplate.findById(savedData.takeCurrentId(), Author.class);
        
        assertNotNull(authorId, "ID should be not null, id: %s");
        assertEquals("a0a79444-d58b-4bbd-b3cd-d267ee4dca13", authorId.takeCurrentId(), "ID must be 'a0a79444-d58b-4bbd-b3cd-d267ee4dca13'");
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

        when(mongoTemplate.findOne(any(Query.class), eq(Author.class))).thenReturn(savedData);

        Author authorEmail = mongoTemplate.findOne(new Query(Criteria.where("email").is(author.takeEmail())), Author.class);

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

        assertAll("Group of author test", 
            () -> assertEquals("a0a79444-d58b-4bbd-b3cd-d267ee4dca13", saveData.takeCurrentId(), "ID must be 'a0a79444-d58b-4bbd-b3cd-d267ee4dca13'"),
            () -> assertEquals("John Doe", saveData.takeFullName(), "Fullname must be 'John Doe'"),
            () -> assertEquals("This is just a test.", saveData.takeBiography(), "Biography must be 'This is just a test.'"),
            () -> assertEquals("johndoe@example.com", saveData.takeEmail(), "Email must be 'johndoe@example.com'"),
            () -> assertEquals("+0217768765", saveData.takePhoneNumber(), "Phone number must be '+0217768765'")
        );
    }
}
