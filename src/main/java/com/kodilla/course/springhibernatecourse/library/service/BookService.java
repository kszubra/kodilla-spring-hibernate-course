package com.kodilla.course.springhibernatecourse.library.service;

import java.util.List;

import com.kodilla.course.springhibernatecourse.library.domain.Book;

public interface BookService {
    List<Book> getBooks();
    void createBook(Book book);
    void deleteBook(int index);
}
