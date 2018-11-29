package com.springframework.spring5webapp.bootstrap;

import com.springframework.spring5webapp.model.Author;
import com.springframework.spring5webapp.model.Book;
import com.springframework.spring5webapp.model.Publisher;
import com.springframework.spring5webapp.model.repositories.AuthorRepository;
import com.springframework.spring5webapp.model.repositories.BookRepository;
import com.springframework.spring5webapp.model.repositories.PublisherRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private AuthorRepository authorRepository;
    private BookRepository bookRepository;
    private PublisherRepository publisherRepository;

    public DevBootstrap(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    private void initData() {
        //Eric
        Author eric = new Author("Eric","Evans");
        Book ddd = new Book("Domain Driven Design", "1234");
        Publisher harper = new Publisher("Harper Collins", "#100  Apt",
                "123 Charming Avenue", "Dallas", "Texas", "75001");
        eric.getBooks().add(ddd);
        ddd.getAuthors().add(eric);
        //Publisher should be saved before Book,
        // else Spring will throw error due to the way the entities are linked
        publisherRepository.save(harper);
        ddd.setPublisher(harper);
        harper.setBook(ddd);

        authorRepository.save(eric);
        bookRepository.save(ddd);
        //Need to save Publisher again after setting book details
        publisherRepository.save(harper);




        //George Orwell
        Author orwell = new Author("George", "Orwell");
        Book animalFarm = new Book("Animal Farm", "4231");
        Publisher penguin = new Publisher("Penguin Books India", "#10", "Downing Street",
                "Westminister", "London", "SW1A 2AB");
        orwell.getBooks().add(animalFarm);
        animalFarm.getAuthors().add(orwell);
        publisherRepository.save(penguin);
        animalFarm.setPublisher(penguin);
        penguin.setBook(animalFarm);


        authorRepository.save(orwell);
        bookRepository.save(animalFarm);
        publisherRepository.save(penguin);

    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }
}
