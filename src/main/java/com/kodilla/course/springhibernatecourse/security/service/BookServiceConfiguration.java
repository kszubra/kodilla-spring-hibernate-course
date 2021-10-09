package com.kodilla.course.springhibernatecourse.security.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kodilla.course.springhibernatecourse.security.repository.BookRepository;

@Configuration
class BookServiceConfiguration {

    @Bean
    BookService bookService(BookRepository repository) {
        return new BookServiceImpl(repository);
    }

}
