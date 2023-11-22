package com.book.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.book.demo.dto.author.AuthorBooksDTO;
import com.book.demo.dto.author.AuthorRequestDTO;
import com.book.demo.dto.author.AuthorUpdateRequestDTO;
import com.book.demo.models.Author;
import com.book.demo.models.Book;
import com.book.demo.repositories.AuthorRepository;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    public Author findAuthorById(String id) {
        return mongoTemplate.findById(id, Author.class);
    }

    public List<Author> findAuthorByName(String name) {
        return mongoTemplate.find(new Query(Criteria.where("name").is(name)), Author.class);
    }

    public Author findAuthorByEmail(String email) {
        return mongoTemplate.findOne(new Query(Criteria.where("email").is(email)), Author.class);
    }

    public AuthorBooksDTO findAuthorBooks(String authorId) {
        Author data = mongoTemplate.findOne(new Query(Criteria.where("_id").is(authorId)), Author.class);
        if (data == null) throw new NullPointerException(String.format("Author with ID %s not found.", authorId));

        List<Book> datas = mongoTemplate.find(new Query(Criteria.where("authorId").is(authorId)), Book.class);
        if (datas.isEmpty()) throw new NullPointerException(String.format("Books with author ID %s not found", authorId));

        AuthorBooksDTO authorBooksDTO = new AuthorBooksDTO(
            data.takeFullName(), 
            data.takeBiography(), 
            data.takeEmail(), 
            data.takePhoneNumber(), 
            datas, 
            data.takeCreatedOn()
        );

        return authorBooksDTO;
    }

    public Author addNewAuthor(AuthorRequestDTO authorRequestDTO) {
        Author storedData = Author.getInstance(
            authorRequestDTO.fullname(),
            authorRequestDTO.biography(),
            authorRequestDTO.email(),
            authorRequestDTO.phoneNumber()
        );
        return authorRepository.save(storedData);
    }

    public void renewAuthor(String id, AuthorUpdateRequestDTO authorUpdateRequestDTO) {
        Update update = new Update();

        update.set("fullname", authorUpdateRequestDTO.fullname());
        update.set("biography", authorUpdateRequestDTO.biography());
        update.set("email", authorUpdateRequestDTO.email());
        update.set("phoneNumber", authorUpdateRequestDTO.phoneNumber());

        mongoTemplate.findAndModify(new Query(Criteria.where("id").is(id)), update, Author.class);
    }

    public void discardAuthors() {
        authorRepository.deleteAll();
    }

    public void discardAuthor(String id) {
        authorRepository.deleteById(id);
    }
}
