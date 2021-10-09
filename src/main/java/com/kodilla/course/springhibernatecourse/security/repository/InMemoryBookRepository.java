package com.kodilla.course.springhibernatecourse.security.repository;

import java.util.ArrayList;
import java.util.List;

import com.kodilla.course.springhibernatecourse.security.domain.Book;

class InMemoryBookRepository implements BookRepository {

    private List<Book> books = new ArrayList<>();

    @Override
    public List<Book> findAll() {
        return books;
    }

    @Override
    public void save(Book book) {
        books.add(book);
    }

    @Override
    public void deleteByIndex(int index) {
        books.remove(index);
    }

}
