package com.book.demo.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.book.demo.dto.publisher.PublisherBooksDTO;
import com.book.demo.dto.publisher.PublisherRequestDTO;
import com.book.demo.dto.publisher.PublisherUpdateRequestDTO;
import com.book.demo.models.Book;
import com.book.demo.models.Publisher;
import com.book.demo.repositories.PublisherRepository;

@Service
public class PublisherService {
    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Publisher> findAllPublishers() {
        return publisherRepository.findAll();
    }

    public Publisher findPublisherById(String id) {
        return mongoTemplate.findById(id, Publisher.class); 
    }

    public Publisher findPublisherByName(String name) {
        return mongoTemplate.findOne(new Query(Criteria.where("name").is(name)), Publisher.class);
    }

    public Publisher findPublisherByEmail(String email) {
        return mongoTemplate.findOne(new Query(Criteria.where("email").is(email)), Publisher.class);
    }

    public PublisherBooksDTO findPublisherBooks(String publisherId) {
        Publisher data = mongoTemplate.findOne(new Query(Criteria.where("_id").is(publisherId)), Publisher.class);
        if (data == null) throw new NullPointerException(String.format("Author with ID %s not found.", publisherId));

        List<Book> datas = mongoTemplate.find(new Query(Criteria.where("publisherId").is(publisherId)), Book.class);
        if (datas.isEmpty()) throw new NullPointerException(String.format("Books with author ID %s not found", publisherId));
        
        PublisherBooksDTO publisherBooksDTO = new PublisherBooksDTO(
            data.getName(), 
            data.getEmail(), 
            data.getBio(), 
            data.getFoundYear(), 
            data.getAddress(), 
            data.getPhoneNumber(), 
            datas, 
            data.getCreatedOn()
        );

        return publisherBooksDTO;
    }

    public Publisher addNewPublisher(PublisherRequestDTO publisherRequestDTO) {
        Publisher storedData = new Publisher(
            UUID.randomUUID().toString(), 
            publisherRequestDTO.name(), 
            publisherRequestDTO.email(), 
            publisherRequestDTO.bio(), 
            publisherRequestDTO.foundYear(), 
            publisherRequestDTO.address(), 
            publisherRequestDTO.phoneNumber(), 
            LocalDateTime.now()
        );

        return publisherRepository.save(storedData);
    }

    public void renewPublisher(String id, PublisherUpdateRequestDTO publisherUpdateRequestDTO) {
        Update update = new Update();

        update.set("name", publisherUpdateRequestDTO.name());
        update.set("email", publisherUpdateRequestDTO.email());
        update.set("bio", publisherUpdateRequestDTO.bio());
        update.set("foundYear", publisherUpdateRequestDTO.foundYear());
        update.set("address", publisherUpdateRequestDTO.address());
        update.set("phoneNumber", publisherUpdateRequestDTO.phoneNumber());
        update.set("createdOn", LocalDateTime.now());

        mongoTemplate.findAndModify(new Query(Criteria.where("_id").is(id)), update, Publisher.class);
    }

    public void discardPublishers() {
        publisherRepository.deleteAll();
    }

    public void discardPublisher(String id) {
        publisherRepository.deleteById(id);
    }
}
