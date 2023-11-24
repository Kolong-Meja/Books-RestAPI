package com.book.demo.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.book.demo.dto.client.ClientDTO;
import com.book.demo.dto.client.ClientLoginDTO;
import com.book.demo.dto.client.ClientPatchPasswordDTO;
import com.book.demo.enums.Role;
import com.book.demo.models.Client;
import com.book.demo.repositories.ClientRepository;

@Service
public class AuthenticationService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String signUp(ClientDTO clientDTO) {
        Client client = Client.getInstance( 
            clientDTO.fullname(), 
            clientDTO.email(), 
            passwordEncoder.encode(clientDTO.password()), 
            Role.USER
        );
        clientRepository.save(client);

        String token = jwtService.generateToken(client);
        
        return token;
    }

    public String signIn(ClientLoginDTO clientLoginDTO) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(clientLoginDTO.email(), clientLoginDTO.password())
        );
        Client client = mongoTemplate.findOne(new Query(Criteria.where("email").is(clientLoginDTO.email())), Client.class);

        if (client == null) throw new UsernameNotFoundException(String.format("Client with email %s not found.", clientLoginDTO.email()));

        String token = jwtService.generateToken(client);

        return token;
    }

    public void renewPassword(String email, ClientPatchPasswordDTO clientPatchPasswordDTO) {
        Client client = mongoTemplate.findOne(new Query(Criteria.where("email").is(email)), Client.class);

        if (client == null) throw new NullPointerException(String.format("Client with email %s not found.", email));

        Update update = new Update();

        update.set("password", passwordEncoder.encode(clientPatchPasswordDTO.password()));
        update.set("createdOn", LocalDateTime.now());

        mongoTemplate.findAndModify(new Query(Criteria.where("email").is(email)), update, Client.class);
    }
}
