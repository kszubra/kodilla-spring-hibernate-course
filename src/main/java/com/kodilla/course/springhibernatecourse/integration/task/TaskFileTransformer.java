package com.kodilla.course.springhibernatecourse.integration.task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TaskFileTransformer {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public String transformFile(String fileName) throws IOException {
        StringBuilder result = new StringBuilder();
        boolean fileExist = new File("data/output/files_add_record.txt").isFile();
        if (fileExist) {
            Files.readAllLines(Paths.get("data/output/files_add_record.txt"))
                    .forEach(line -> result.append(line).append("\n"));
        }
        File file = new File(fileName);
        result.append(String.format("[%s]", LocalDateTime.now().format(formatter)))
                .append(String.format(" %s", file.getName()))
                .append("\n");
        return result.toString().strip();
    }
}
