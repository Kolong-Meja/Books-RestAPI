package com.book.demo;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.book.demo.repositories.PublisherRepository;

public class PublisherServiceTest {
    private PublisherRepository publisherRepository;
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUpService() {
        publisherRepository = mock(PublisherRepository.class);
        mongoTemplate = mock(MongoTemplate.class);
    }

    @Test
    void findAllPublishers() {}

    @Test
    void findPublisherById() {}

    @Test
    void findPublisherName() {}

    @Test
    void findPublisherByEmail() {}

    @Test
    void findPublisherBooks() {}

    @Test
    void createNewPublisher() {}

    @Test
    void updateOnePublisher() {}

    @Test
    void removeOnePublisher() {}

    @Test
    void removeAllPublishers() {}
}
