package com.book.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.book.demo.dto.client.ClientDTO;
import com.book.demo.dto.client.ClientLoginDTO;
import com.book.demo.dto.thrives.AuthenticationResponse;
import com.book.demo.models.Client;
import com.book.demo.models.Role;
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

    public AuthenticationResponse signUp(ClientDTO clientDTO) {
        Client client = Client.getInstance( 
            clientDTO.fullname(), 
            clientDTO.email(), 
            passwordEncoder.encode(clientDTO.password()), 
            Role.USER
        );
        clientRepository.save(client);

        String token = jwtService.generateToken(client);
        
        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse signIn(ClientLoginDTO clientLoginDTO) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(clientLoginDTO.email(), clientLoginDTO.password())
        );
        Client client = mongoTemplate.findOne(new Query(Criteria.where("email").is(clientLoginDTO.email())), Client.class);

        if (client == null) throw new UsernameNotFoundException(String.format("Client with email %s not found.", clientLoginDTO.email()));

        String token = jwtService.generateToken(client);

        return new AuthenticationResponse(token);
    }
}
