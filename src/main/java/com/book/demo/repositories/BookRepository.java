package com.book.demo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.book.demo.models.Book;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {}
