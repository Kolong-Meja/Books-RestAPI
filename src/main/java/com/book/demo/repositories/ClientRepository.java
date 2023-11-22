package com.book.demo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.book.demo.models.Client;

@Repository
public interface ClientRepository extends MongoRepository<Client, String> {}
