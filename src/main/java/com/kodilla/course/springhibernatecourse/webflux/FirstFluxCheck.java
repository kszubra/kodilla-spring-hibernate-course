package com.kodilla.course.springhibernatecourse.webflux;

import reactor.core.publisher.Flux;

public class FirstFluxCheck {

    public static void main(String[] args) {
        WebFluxBook b1 = new WebFluxBook("Title1", "Author1", 2000);
        WebFluxBook b2 = new WebFluxBook("Title2", "Author2", 2001);

        Flux<WebFluxBook> bookFlux = Flux.just(b1, b2);
        /**
         * flux potrafi z łatwością dołączać kolejne dane do transmisji przy pomocy metody concatWith(...)
         */
        bookFlux = bookFlux.concatWith(Flux.error(new Exception("Test exception")));
        bookFlux.subscribe(System.out::println, FirstFluxCheck::handleException);
    }

    private static void handleException(Throwable e) {
        System.out.println("There was an error: " + e);
    }
}
