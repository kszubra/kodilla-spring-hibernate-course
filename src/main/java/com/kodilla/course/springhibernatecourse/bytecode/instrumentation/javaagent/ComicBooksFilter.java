package com.kodilla.course.springhibernatecourse.bytecode.instrumentation.javaagent;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.kodilla.course.springhibernatecourse.bytecode.instrumentation.bytebuddy.ComicBook;

public class ComicBooksFilter {

    private final List<ComicBook> books;

    public ComicBooksFilter(List<ComicBook> books) {
        this.books = books;

    }

    public List<ComicBook> onlyBooksOlderThan(int years) {
        return books.stream()
                .filter(b -> b.getYear() < LocalDate.now().getYear() - years)
                .collect(Collectors.toList());
    }

}
