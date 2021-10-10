package com.kodilla.course.springhibernatecourse.integration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileTransformer {

    //zadaniem jest odczytanie zawartości pliku tekstowego i odwrócenie kolejności linii w tym pliku – ostatnia stanie się pierwszą, przedostatnia – drugą itd.
    // Wynik zwracany jest w postaci zmiennej typu String.
    public String transformFile(String fileName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName));
        StringBuilder result = new StringBuilder();

        for (int n = lines.size() - 1; n >= 0; n--) {
            result.append(lines.get(n) + "\n");
        }

        return result.toString();
    }

}
