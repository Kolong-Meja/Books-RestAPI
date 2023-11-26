package com.book.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.book.demo.enums.Role;
import com.book.demo.models.Client;
import com.book.demo.repositories.ClientRepository;

public class ClientServiceTest {
    private ClientRepository clientRepository;
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUpService() {
        clientRepository = mock(ClientRepository.class);
        mongoTemplate = mock(MongoTemplate.class);
    }

    @Test
    void findClientById() {
        Client client = Client.getInstance(
            "c58b5343-a936-4a9a-986f-b4ccb0ca2996", 
            "John Doe", 
            "johndoe@example.com", 
            "Example08#", 
            Role.USER,
            LocalDateTime.now()
        );

        when(clientRepository.save(any(Client.class))).thenReturn(client);

        clientRepository.save(client);

        verify(clientRepository).save(client);

        when(mongoTemplate.findById(eq("c58b5343-a936-4a9a-986f-b4ccb0ca2996"), eq(Client.class))).thenReturn(client);

        Client data = mongoTemplate.findById(client.getCurrentId(), Client.class);

        verify(mongoTemplate).findById(anyString(), eq(Client.class));

        assertNotNull(data, "Data cannot be null.");
        assertEquals("c58b5343-a936-4a9a-986f-b4ccb0ca2996", data.getCurrentId(), "ID must be 'c58b5343-a936-4a9a-986f-b4ccb0ca2996'");
    }

    @Test
    void findClientByEmail() {}

    @Test
    void updateOneClient() {}

    @Test
    void changeNewPassword() {}

    @Test
    void removeOneAuthor() {}
}
