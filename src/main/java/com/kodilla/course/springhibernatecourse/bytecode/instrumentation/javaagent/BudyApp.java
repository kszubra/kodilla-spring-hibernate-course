package com.kodilla.course.springhibernatecourse.bytecode.instrumentation.javaagent;

import java.util.List;

import com.kodilla.course.springhibernatecourse.bytecode.instrumentation.bytebuddy.ComicBook;

public class BudyApp {
    public static void main(String[] args) {

        /**
         * Jej zadaniem będzie stworzenie listy stu losowych książek,
         * a następnie przefiltrowanie tej listy w taki sposób, aby wziąć pod uwagę jedynie te książki, które zostały wydane dwadzieścia lat temu lub dawniej.
         */

        List<ComicBook> comicBooks = ComicBooksGenerator.generate(100);
        ComicBooksFilter comicBooksFilter = new ComicBooksFilter(comicBooks);
        List<ComicBook> filteredComicBooks = comicBooksFilter.onlyBooksOlderThan(20);
        System.out.println(filteredComicBooks.size());

    }
}
