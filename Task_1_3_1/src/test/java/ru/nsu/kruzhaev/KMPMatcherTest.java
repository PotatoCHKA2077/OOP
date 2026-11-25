package ru.nsu.kruzhaev;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class KMPMatcherTest {
    @TempDir
    Path tempDir;

    private Path createFile(String content) throws IOException {
        Path file = tempDir.resolve("input.txt");
        Files.writeString(file, content, StandardCharsets.UTF_8);
        return file;
    }

    @Test
    void shouldFindSingleOccurrence() throws IOException {
        Path file = createFile("hello world");

        SubstringFinder finder = new SubstringFinder("world");

        assertEquals(List.of(6L), finder.find(file));
    }

    @ParameterizedTest
    @CsvSource({
            "'hello world', world, 6",
            "'abcdef', xyz, ''",
            "'abcabc', abc, 0;3",
            "'aaaaa', aaa, 0;1;2",
            "'абракадабра', бра, 1;8",
            "'abc', abcdef, ''",
            "'', abc, ''"
    })
    void shouldFindOccurrences(String text, String pattern, String expected) throws IOException {
        Path file = createFile(text);

        SubstringFinder finder = new SubstringFinder(pattern);

        List<Long> actual = finder.find(file);

        List<Long> expectedPositions = expected.isEmpty() ? List.of()
                : Arrays.stream(expected.split(";")).map(Long::parseLong).toList();

        assertEquals(expectedPositions, actual);
    }

    @Test
    void shouldFindOccurrencesWithSurrogatePairs() throws IOException {
        Path file = createFile("😀a😀a");

        SubstringFinder finder = new SubstringFinder("😀a");

        assertEquals(List.of(0L, 2L), finder.find(file));
    }

    @Test
    void shouldResetStateBetweenSearches() throws IOException {
        Path firstFile = createFile("aaaaa");
        Path secondFile = tempDir.resolve("second.txt");

        Files.writeString(secondFile, "aaa", StandardCharsets.UTF_8);

        SubstringFinder finder = new SubstringFinder("aa");

        assertEquals(List.of(0L, 1L, 2L, 3L), finder.find(firstFile));
        assertEquals(List.of(0L, 1L), finder.find(secondFile));
    }

    @Test
    void shouldFindPatternAcrossByteBufferBoundary() throws IOException {
        String text = "a".repeat(4095) + "XYZ";

        Path file = createFile(text);

        SubstringFinder finder = new SubstringFinder("XYZ");

        assertEquals(List.of(4095L), finder.find(file));
    }

    @Test
    void shouldDecodeUtf8CharacterAcrossByteBufferBoundary() throws IOException {
        String text = "a".repeat(4095) + "😀" + "z";
        Path file = createFile(text);

        SubstringFinder finder = new SubstringFinder("😀");

        assertEquals(List.of(4095L), finder.find(file));
    }

    @Test
    void shouldThrowExceptionForMalformedUtf8() throws IOException {
        Path file = tempDir.resolve("malformed.txt");

        Files.write(file, new byte[] {(byte) 0xC3, (byte) 0x28});

        SubstringFinder finder = new SubstringFinder("a");

        assertThrows(CharacterCodingException.class, () -> finder.find(file));
    }

    @Test
    void shouldProcessFileLargerThanBuffers() throws IOException {
        String text = "a".repeat(20_000) + "XYZ" + "b".repeat(20_000) + "XYZ";

        Path file = createFile(text);

        SubstringFinder finder = new SubstringFinder("XYZ");

        assertEquals(List.of(20_000L, 40_003L), finder.find(file));
    }

    @Test
    void shouldThrowExceptionWhenFileDoesNotExist() {
        SubstringFinder finder = new SubstringFinder("abc");

        assertThrows(IOException.class, () -> finder.find(tempDir.resolve("missing.txt"))
        );
    }

    @Test
    void shouldReturnEmptyListForEmptyFile() throws IOException {
        Path file = createFile("");

        SubstringFinder finder = new SubstringFinder("abc");

        assertEquals(List.of(), finder.find(file));
    }

    @Test
    void shouldThrowExceptionWhenEmptyPattern() {
        assertThrows(IllegalArgumentException.class, () -> new SubstringFinder(""));
    }
}