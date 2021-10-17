package com.kodilla.course.springhibernatecourse.bytecode.instrumentation.javaagent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.kodilla.course.springhibernatecourse.bytecode.instrumentation.bytebuddy.ComicBook;

public class ComicBooksGenerator {

    private static final Random random = new Random();

    public static List<ComicBook> generate(int howMuch) {
        List<ComicBook> books = new ArrayList<>();
        for(int n = 0; n < howMuch; n++)
            books.add(randomBook());
        return books;
    }

    private ComicBooksGenerator() {

    }

    private static ComicBook randomBook() {
        return new ComicBook(randomTitle(), randomAuthor(), random.nextInt(30) + 1980);
    }

    private static String randomAuthor() {
        return randomString(10, 20);
    }

    private static String randomTitle() {
        return randomString(5, 30);
    }

    private static String randomString(int minLen, int maxLen) {
        int howLong = random.nextInt(maxLen - minLen) + minLen;
        StringBuilder builder = new StringBuilder();

        for (int n = 0; n < howLong; n++) {
            char c = (char) (random.nextInt(90-65) + 65);
            builder.append(c);
        }

        return builder.toString();

    }

}
