package com.kodilla.course.springhibernatecourse.integration.task;

import static org.springframework.integration.file.support.FileExistsMode.REPLACE;

import java.io.File;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.FileWritingMessageHandler;

import com.kodilla.course.springhibernatecourse.integration.FileTransformer;

@ConditionalOnProperty(
        value = "integration.config.profile",
        havingValue = "TASK"
)
@Configuration
public class TaskFileIntegrationConfiguration {

    @Bean
    IntegrationFlow fileIntegrationFlow(
            FileReadingMessageSource fileAdapter,
            FileTransformer transformer,
            FileWritingMessageHandler outputFileHandler
    ) {
        return IntegrationFlows.from(fileAdapter, config -> config.poller(Pollers.fixedDelay(1000)))
                .transform(transformer, "transformFile")
                .handle(outputFileHandler)
                .get();
    }

    @Bean
    FileReadingMessageSource fileAdapter() {
        FileReadingMessageSource fileSource = new FileReadingMessageSource();
        fileSource.setDirectory(new File("data/input"));

        return fileSource;
    }

    @Bean
    TaskFileTransformer transformer() {
        return new TaskFileTransformer();
    }

    @Bean
    FileWritingMessageHandler outputFileAdapter() {
        File directory = new File("data/output");
        FileWritingMessageHandler handler = new FileWritingMessageHandler(directory);
        handler.setExpectReply(false);
        handler.setAppendNewLine(true);
        handler.setFileExistsMode(REPLACE);
        handler.setFileNameGenerator(message -> "files_add_record.txt");
        return handler;
    }
}
