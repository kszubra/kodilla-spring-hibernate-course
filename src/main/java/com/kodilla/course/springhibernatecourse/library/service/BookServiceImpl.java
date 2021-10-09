package com.kodilla.course.springhibernatecourse.library.service;

import java.util.List;

import com.kodilla.course.springhibernatecourse.library.domain.Book;
import com.kodilla.course.springhibernatecourse.library.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class BookServiceImpl implements BookService {

    private final BookRepository repository;

    @Override
    public List<Book> getBooks() {
        return repository.findAll();
    }

    @Override
    public void createBook(Book book) {
        repository.save(book);
    }

    @Override
    public void deleteBook(int index) {
        repository.deleteByIndex(index);
    }

}
