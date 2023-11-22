package com.book.demo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.book.demo.models.Publisher;

@Repository
public interface PublisherRepository extends MongoRepository<Publisher, String>{}
