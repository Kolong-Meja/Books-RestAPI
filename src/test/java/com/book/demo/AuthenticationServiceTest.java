package com.book.demo;

import static org.junit.jupiter.api.Assertions.assertAll;
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
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.UserDetails;

import com.book.demo.enums.Role;
import com.book.demo.models.Client;
import com.book.demo.repositories.ClientRepository;
import com.book.demo.services.JwtService;

public class AuthenticationServiceTest {
    private ClientRepository clientRepository;
    private MongoTemplate mongoTemplate;
    private JwtService jwtService;

    @BeforeEach
    void setUpService() {
        clientRepository = mock(ClientRepository.class);
        mongoTemplate = mock(MongoTemplate.class);
        jwtService = mock(JwtService.class);
    }

    @Test
    void signUpAccount() {
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

        assertNotNull(client, "Data cannot be null.");
        assertAll("Group of authentication service test", 
            () -> assertEquals("c58b5343-a936-4a9a-986f-b4ccb0ca2996", client.getCurrentId(), "ID must be 'c58b5343-a936-4a9a-986f-b4ccb0ca2996'"),
            () -> assertEquals("John Doe", client.getFullName(), "Fullname must be 'John Doe'"),
            () -> assertEquals("johndoe@example.com", client.getEmail(), "Email must be 'johndoe@example.com'"),
            () -> assertEquals("Example08#", client.getPassword(), "Password must be 'Example08#'"),
            () -> assertEquals(Role.USER, client.getRole(), "Role must be 'USER'")
        );

        when(jwtService.generateToken(any(UserDetails.class))).thenReturn(anyString());

        String authToken = jwtService.generateToken(client);

        verify(jwtService).generateToken(client);

        assertNotNull(authToken, "Token cannot be null.");
    }

    @Test
    void signInAccount() {
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

        assertNotNull(client, "Data cannot be null.");
        assertAll("Group of authentication service test", 
            () -> assertEquals("c58b5343-a936-4a9a-986f-b4ccb0ca2996", client.getCurrentId(), "ID must be 'c58b5343-a936-4a9a-986f-b4ccb0ca2996'"),
            () -> assertEquals("John Doe", client.getFullName(), "Fullname must be 'John Doe'"),
            () -> assertEquals("johndoe@example.com", client.getEmail(), "Email must be 'johndoe@example.com'"),
            () -> assertEquals("Example08#", client.getPassword(), "Password must be 'Example08#'"),
            () -> assertEquals(Role.USER, client.getRole(), "Role must be 'USER'")
        );

        when(mongoTemplate.findOne(any(Query.class), eq(Client.class))).thenReturn(client);

        Client data = mongoTemplate.findOne(new Query(Criteria.where("email").is(client.getEmail())), Client.class);

        verify(mongoTemplate).findOne(any(Query.class), eq(Client.class));

        when(jwtService.generateToken(any(UserDetails.class))).thenReturn(anyString());

        String authToken = jwtService.generateToken(data);

        verify(jwtService).generateToken(data);

        assertNotNull(authToken, "Token cannot be null.");
    }

    @Test
    void renewPassword() {
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

        assertNotNull(client, "Data cannot be null.");
        assertAll("Group of authentication service test", 
            () -> assertEquals("c58b5343-a936-4a9a-986f-b4ccb0ca2996", client.getCurrentId(), "ID must be 'c58b5343-a936-4a9a-986f-b4ccb0ca2996'"),
            () -> assertEquals("John Doe", client.getFullName(), "Fullname must be 'John Doe'"),
            () -> assertEquals("johndoe@example.com", client.getEmail(), "Email must be 'johndoe@example.com'"),
            () -> assertEquals("Example08#", client.getPassword(), "Password must be 'Example08#'"),
            () -> assertEquals(Role.USER, client.getRole(), "Role must be 'USER'")
        );

        when(mongoTemplate.findOne(any(Query.class), eq(Client.class))).thenReturn(client);

        Client data = mongoTemplate.findOne(new Query(Criteria.where("email").is(client.getEmail())), Client.class);

        verify(mongoTemplate).findOne(any(Query.class), eq(Client.class));

        assertNotNull(data, "Data cannot be null.");
        assertEquals("johndoe@example.com", data.getEmail(), "Email must be 'johndoe@example.com'");

        data.changePassword("Example09#");

        when(clientRepository.save(any(Client.class))).thenReturn(data);

        clientRepository.save(data);

        assertNotNull(data.getPassword(), "Password cannot be null");
        assertEquals("Example09#", data.getPassword(), "Password must be 'Example09#'");
    }
}
