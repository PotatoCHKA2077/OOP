package ru.nsu.kruzhaev;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;


public class Main {
    public static List<Long> find(String filename, String string) throws IOException {
        KMPMatcher matcher = new KMPMatcher(string);
        Path path = Path.of(filename);
        return matcher.find(path);
    }

    static void main() throws IOException {
        System.out.println(find("src/main/resources/file.txt", "бра").toString());
    }
}
