package ru.nsu.kruzhaev;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Класс Main
 */
public class Main {
    /**
     * Функция для поиска подстроки в файле.
     *
     * @param filename имя файла.
     * @param string подстрока, которую надо найти.
     * @return список индексов начала каждого вхождения.
     * @throws IOException исключение ввода-вывода.
     */
    public static List<Long> find(String filename, String string) throws IOException {
        SubstringFinder matcher = new SubstringFinder(string);
        Path path = Path.of(filename);
        return matcher.find(path);
    }


    static void main() throws IOException {
        System.out.println(find("src/main/resources/file.txt", "бра").toString());
    }
}
