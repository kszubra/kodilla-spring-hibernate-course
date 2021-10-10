package com.kodilla.course.springhibernatecourse.integration;

import java.io.File;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;

@Configuration
public class FileIntegrationConfiguration {

    @Bean
    IntegrationFlow fileIntegrationFlow(
            FileReadingMessageSource fileAdapter,
            FileTransformer transformer,
            FileWritingMessageHandler outputFileHandler
    ) {
        return IntegrationFlows.from(fileAdapter, config -> config.poller(Pollers.fixedDelay(1000))) //adapter wczytujący nazwy plików musi zostać wyposażony w tzw. poller, czyli klasę określającą, w jaki sposób przepływ ma być inicjowany. Ustawimy stały odczyt plików co jedną sekundę, wykorzystując klasę narzędziową Pollers i jej metodę fixedDelay(), zwracającą poller inicjujący przepływ co ustalony czas
                .transform(transformer, "transformFile")
                .handle(outputFileHandler)
                .get();
    }

    //Oprócz FileReadingMessageSource biblioteka Spring Integration daje nam do dyspozycji całkiem szeroki wybór innych adapterów.
    // Wszystkie one dziedziczą z klasy AbstractMessageSource – możesz w jej dokumentacji sprawdzić jakie inne klasy z niej dziedziczą
    // (link: https://docs.spring.io/spring-integration/api/org/springframework/integration/endpoint/AbstractMessageSource.html).
    @Bean
    FileReadingMessageSource fileAdapter() {
        FileReadingMessageSource fileSource = new FileReadingMessageSource();
        fileSource.setDirectory(new File("data/input"));

        return fileSource;
    }

    @Bean
    FileTransformer transformer() {
        return new FileTransformer();
    }

    //Zapisanie pliku zrealizujemy przy pomocy metody handle naszego przepływu integracyjnego. Parametrem tej metody będzie handler plikowy
    //Analogicznie jak przy adapterach – również i tutaj mamy do dyspozycji szereg innych handlerów do wyboru.
    // Poszczególne handlery rozszerzają klasę AbstractReplyProducingMessageHandler (link: https://docs.spring.io/spring-integration/api/org/springframework/integration/handler/AbstractReplyProducingMessageHandler.
    @Bean
    FileWritingMessageHandler outputFileAdapter() {
        File directory = new File("data/output");
        FileWritingMessageHandler handler = new FileWritingMessageHandler(directory);
        handler.setExpectReply(false);

        return handler;
    }
}
