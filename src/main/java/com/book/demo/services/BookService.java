package com.book.demo.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.book.demo.dto.book.BookPatchAuthorIdDTO;
import com.book.demo.dto.book.BookPatchPublisherIdDTO;
import com.book.demo.dto.book.BookPatchUpdateDTO;
import com.book.demo.dto.book.BookRequestDTO;
import com.book.demo.dto.book.BookUpdateRequestDTO;
import com.book.demo.models.Author;
import com.book.demo.models.Book;
import com.book.demo.models.Publisher;
import com.book.demo.repositories.BookRepository;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public final List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public final Book findBookById(String id) {
        return mongoTemplate.findById(id, Book.class);
    }

    public List<Book> findBookByTitle(String title) {
        return mongoTemplate.find(new Query(Criteria.where("title").is(title)), Book.class);
    }

    public List<Book> findBookByAuthorId(String authorId) {
        return mongoTemplate.find(new Query(Criteria.where("authorId").is(authorId)), Book.class);
    }

    public List<Book> findBookByPublisherId(String publisherId) {
        return mongoTemplate.find(new Query(Criteria.where("publisherId").is(publisherId)), Book.class);
    }

    public Book addNewBook(BookRequestDTO bookRequestDTO) {
        Author author = mongoTemplate.findOne(new Query(Criteria.where("_id").is(bookRequestDTO.authorId())), Author.class);
        if (author == null) throw new NullPointerException(String.format("Author with ID %s not found.", bookRequestDTO.authorId()));

        Publisher publisher = mongoTemplate.findOne(new Query(Criteria.where("_id").is(bookRequestDTO.publisherId())), Publisher.class);
        if (publisher == null) throw new NullPointerException(String.format("Publisher with ID %s not found.", bookRequestDTO.publisherId()));
        
        Book storedData = Book.getInstance(
            bookRequestDTO.isbn(),
            bookRequestDTO.title(),
            bookRequestDTO.synopsis(),
            author.takeCurrentId(),
            bookRequestDTO.publisherId(),
            bookRequestDTO.publishYear(),
            bookRequestDTO.totalPage(),
            bookRequestDTO.price(),
            bookRequestDTO.category()
        );
        return bookRepository.save(storedData);
    }

    public void renewBook(String id, BookUpdateRequestDTO bookUpdateRequestDTO) {
        Author author = mongoTemplate.findOne(new Query(Criteria.where("_id").is(bookUpdateRequestDTO.authorId())), Author.class);
        if (author == null) throw new NullPointerException(String.format("Author with ID %s not found.", bookUpdateRequestDTO.authorId()));

        Publisher publisher = mongoTemplate.findOne(new Query(Criteria.where("_id").is(bookUpdateRequestDTO.publisherId())), Publisher.class);
        if (publisher == null) throw new NullPointerException(String.format("Publisher with ID %s not found.", bookUpdateRequestDTO.publisherId()));
        
        Update update = new Update();
        
        update.set("isbn", bookUpdateRequestDTO.isbn());
        update.set("title", bookUpdateRequestDTO.title());
        update.set("synopsis", bookUpdateRequestDTO.synopsis());
        update.set("authorId", author.takeCurrentId());
        update.set("publisherId", bookUpdateRequestDTO.publisherId());
        update.set("publishYear", bookUpdateRequestDTO.publishYear());
        update.set("totalPage", bookUpdateRequestDTO.totalPage());
        update.set("price", bookUpdateRequestDTO.price());
        update.set("category", bookUpdateRequestDTO.category());
        update.set("createdOn", LocalDateTime.now());

        mongoTemplate.findAndModify(new Query(Criteria.where("_id").is(id)), update, Book.class);
    }

    public void renewBook(String id, BookPatchUpdateDTO bookPatchUpdateDTO) {
        Update update = new Update();
        
        update.set("isbn", bookPatchUpdateDTO.isbn());
        update.set("title", bookPatchUpdateDTO.title());
        update.set("synopsis", bookPatchUpdateDTO.synopsis());
        update.set("publishYear", bookPatchUpdateDTO.publishYear());
        update.set("totalPage", bookPatchUpdateDTO.totalPage());
        update.set("price", bookPatchUpdateDTO.price());
        update.set("category", bookPatchUpdateDTO.category());
        update.set("createdOn", LocalDateTime.now());

        mongoTemplate.findAndModify(new Query(Criteria.where("_id").is(id)), update, Book.class);
    }

    public void renewAuthor(String id, BookPatchAuthorIdDTO bookPatchAuthorIdDTO) {
        Author author = mongoTemplate.findOne(new Query(Criteria.where("_id").is(bookPatchAuthorIdDTO.authorId())), Author.class);
        if (author == null) throw new NullPointerException(String.format("Author with ID %s not found.", bookPatchAuthorIdDTO.authorId()));
        
        Update update = new Update();
        update.set("authorId", author.takeCurrentId());
        update.set("createdOn", LocalDateTime.now());
        
        mongoTemplate.findAndModify(new Query(Criteria.where("_id").is(id)), update, Book.class);
    }

    public void renewPublisher(String id, BookPatchPublisherIdDTO bookPatchPublisherIdDTO) {
        Publisher publisher = mongoTemplate.findOne(new Query(Criteria.where("_id").is(bookPatchPublisherIdDTO.publisherId())), Publisher.class);
        if (publisher == null) throw new NullPointerException(String.format("Publisher with ID %s not found.", bookPatchPublisherIdDTO.publisherId()));

        Update update = new Update();
        update.set("publisherId", bookPatchPublisherIdDTO.publisherId());
        update.set("createdOn", LocalDateTime.now());
        
        mongoTemplate.findAndModify(new Query(Criteria.where("_id").is(id)), update, Book.class);
    }

    public void discardBooks() {
        bookRepository.deleteAll();
    }

    public void discardBook(String id) {
        bookRepository.deleteById(id);
    }
}
