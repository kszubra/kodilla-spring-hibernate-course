package com.kodilla.course.springhibernatecourse.httpconverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

public class MyCustomSlashConverter implements HttpMessageConverter<Object> {


    /**
    canRead oraz canWrite – przy pomocy tych metod nasza klasa konwertera informuje Springa, jakiego typu obiekty potrafi konwertować
    i przy pomocy jakiego MediaType są zapisane. W naszym przypadku chodzi o obiekty klasy MyCustomClass, a także o MediaType równe TEXT_PLAIN
     */
    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return clazz.getName().equals("com.kodilla.course.springhibernatecourse.httpconverter.MyCustomClass") &&
                mediaType.getSubtype().equals("plain") && mediaType.getType().equals("text");

    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return clazz.getName().equals("com.kodilla.course.springhibernatecourse.httpconverter.MyCustomClass") &&
                mediaType.getSubtype().equals("plain") && mediaType.getType().equals("text");

    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return List.of(MediaType.ALL);
    }

    @Override
    public Object read(Class<?> clazz, HttpInputMessage inputMessage) throws IOException {
        StringBuilder builder = new StringBuilder();

        try (Reader reader = new BufferedReader(
                new InputStreamReader(
                        inputMessage.getBody(),
                        Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c = 0;

            while ((c = reader.read()) != -1)
                builder.append((char) c);
        }

        String[] fields = builder.toString().split("/");

        return new MyCustomClass(fields[0], fields[1], fields[2]);
    }

    /**
     * Metodę write pozostawimy z jej domyślną, "pustą" implementacją, ponieważ interesuje nas możliwość odczytywania
     * danych ze strumienia wejściowego i tworzenia obiektów Dto, a nie na odwrót.
     */
    @Override
    public void write(
            Object o, MediaType contentType, HttpOutputMessage outputMessage) throws IOException,
            HttpMessageNotWritableException {

    }

}
