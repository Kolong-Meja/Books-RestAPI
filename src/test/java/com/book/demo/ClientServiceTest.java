package com.book.demo;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

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
    void findClientByEmail() {
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

        when(mongoTemplate.findOne(any(Query.class), eq(Client.class))).thenReturn(client);
        
        Client data = mongoTemplate.findOne(new Query(Criteria.where("email").is("johndoe@example.com")), Client.class);

        verify(mongoTemplate).findOne(any(Query.class), eq(Client.class));

        assertNotNull(data, "Data cannot be null.");
        assertEquals("johndoe@example.com", data.getEmail(), "Email must be 'johndoe@example.com'");
    }

    @Test
    void updateOneClient() {
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

        when(mongoTemplate.findById(eq("c58b5343-a936-4a9a-986f-b4ccb0ca2996"), eq(Client.class))).thenReturn(client);

        Client data = mongoTemplate.findById(client.getCurrentId(), Client.class);

        verify(mongoTemplate).findById(anyString(), eq(Client.class));

        assertNotNull(data, "Data cannot be null.");
        assertEquals("c58b5343-a936-4a9a-986f-b4ccb0ca2996", data.getCurrentId(), "ID must be 'c58b5343-a936-4a9a-986f-b4ccb0ca2996'");

        data.changeFullName("John Smith");
        data.changeEmail("johnsmith78@example.com");
        data.changePassword("Example09#");
        data.changeRole(Role.USER);
        data.changeCreatedOn(LocalDateTime.of(2023, 11, 26, 7, 32, 54));

        when(clientRepository.save(any(Client.class))).thenReturn(data);

        clientRepository.save(data);

        assertNotNull(data, "Data cannot be null.");
        assertAll("Group of client test", 
            () -> assertEquals("c58b5343-a936-4a9a-986f-b4ccb0ca2996", data.getCurrentId(), "ID must be 'a0a79444-d58b-4bbd-b3cd-d267ee4dca13'"),
            () -> assertEquals("John Smith", data.getFullName(), "Fullname must be 'John Smith'"),
            () -> assertEquals("johnsmith78@example.com", data.getEmail(), "Email must be 'johnsmith78@example.com'"),
            () -> assertEquals("Example09#", data.getPassword(), "Password must be 'Example09#'"),
            () -> assertEquals(Role.USER, data.getRole(), "Role must be 'USER'")
        );
    }

    @Test
    void changeNewPassword() {
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

        when(mongoTemplate.findById(eq("c58b5343-a936-4a9a-986f-b4ccb0ca2996"), eq(Client.class))).thenReturn(client);
        
        Client data = mongoTemplate.findById(client.getCurrentId(), Client.class);

        verify(mongoTemplate).findById(anyString(), eq(Client.class));

        assertNotNull(data, "Data cannot be null.");
        assertEquals("c58b5343-a936-4a9a-986f-b4ccb0ca2996", data.getCurrentId(), "ID must be 'c58b5343-a936-4a9a-986f-b4ccb0ca2996'");

        data.changePassword("Example09#");

        when(clientRepository.save(any(Client.class))).thenReturn(data);

        clientRepository.save(data);

        assertNotNull(data.getPassword(), "Password cannot be null.");
        assertEquals("Example09#", data.getPassword(), "Password must be 'Example09#'");
    }

    @Test
    void removeOneAuthor() {
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

        clientRepository.deleteById(client.getCurrentId());

        verify(clientRepository).deleteById(client.getCurrentId());

        Client deletedData = mongoTemplate.findById(eq("c58b5343-a936-4a9a-986f-b4ccb0ca2996"), eq(Client.class));

        assertNull(deletedData, "Data should be null now.");
    }
}
