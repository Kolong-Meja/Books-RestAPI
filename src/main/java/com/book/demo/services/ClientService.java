package com.book.demo.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.book.demo.dto.client.ClientPatchPasswordDTO;
import com.book.demo.dto.client.ClientUpdateRequestDTO;
import com.book.demo.models.Client;
import com.book.demo.repositories.ClientRepository;

@Service
public class ClientService implements UserDetailsService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ClientRepository clientRepository;

    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Client client = mongoTemplate.findOne(new Query(Criteria.where("email").is(email)), Client.class);

        if (client == null) throw new UsernameNotFoundException(String.format("Client with email %s not found.", email));

        return client;
    }

    public Client findClientById(String id) {
        return mongoTemplate.findById(id, Client.class);
    }

    public Client findClientByEmail(String email) {
        return mongoTemplate.findOne(new Query(Criteria.where("email").is(email)), Client.class);
    }

    public void renewClient(String id, ClientUpdateRequestDTO clientUpdateRequestDTO) {
        Client client = mongoTemplate.findById(id, Client.class);

        if (client == null) throw new NullPointerException(String.format("Client with ID %s not found.", id));

        Update update = new Update();

        update.set("fullname", clientUpdateRequestDTO.fullname());
        update.set("email", clientUpdateRequestDTO.email());
        update.set("password", passwordEncoder.encode(clientUpdateRequestDTO.password()));
        update.set("createdOn", LocalDateTime.now());

        mongoTemplate.findAndModify(new Query(Criteria.where("_id").is(id)), update, Client.class);
    }

    public void changeNewPassword(String id, ClientPatchPasswordDTO clientPatchPasswordDTO) {
        Client client = mongoTemplate.findById(id, Client.class);

        if (client == null) throw new NullPointerException(String.format("Client with ID %s not found.", id));

        Update update = new Update();

        update.set("password", passwordEncoder.encode(clientPatchPasswordDTO.password()));
        update.set("createdOn", LocalDateTime.now());

        mongoTemplate.findAndModify(new Query(Criteria.where("_id").is(id)), update, Client.class);
    }

    public void discardClient(String id) {
        Client client = mongoTemplate.findById(id, Client.class);

        if (client == null) throw new NullPointerException(String.format("Client with ID %s not found.", id));

        clientRepository.deleteById(id);
    }
}
