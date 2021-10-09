package com.kodilla.course.springhibernatecourse.security.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RepositoryConfiguration {

    @Bean
    BookRepository repository() {
        return new InMemoryBookRepository();
    }

}
