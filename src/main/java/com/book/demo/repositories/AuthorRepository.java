package com.book.demo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.book.demo.models.Author;

@Repository
public interface AuthorRepository extends MongoRepository<Author, String>{}
