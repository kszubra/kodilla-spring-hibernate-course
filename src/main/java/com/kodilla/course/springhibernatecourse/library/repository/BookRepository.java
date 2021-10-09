package com.kodilla.course.springhibernatecourse.library.repository;

import java.util.List;

import com.kodilla.course.springhibernatecourse.library.domain.Book;

public interface BookRepository {

    List<Book> findAll();
    void save(Book book);
    void deleteByIndex(int index);

}
