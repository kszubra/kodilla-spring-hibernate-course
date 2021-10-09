package com.kodilla.course.springhibernatecourse.library.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RepositoryConfiguration {

    @Bean
    BookRepository repository() {
        return new InMemoryBookRepository();
    }

}
