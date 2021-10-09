package com.kodilla.course.springhibernatecourse.security.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class Book {
    String title;
    String author;
    int year;
}
