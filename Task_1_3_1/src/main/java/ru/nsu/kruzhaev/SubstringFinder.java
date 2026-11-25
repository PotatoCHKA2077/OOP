package ru.nsu.kruzhaev;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс поиска подстроки.
 */
public class SubstringFinder {
    private final int[] substring;
    private final int[] prefixFunction;
    private int j;
    private long position;
    private List<Long> result;
    private FileChannel channel;
    private CharsetDecoder decoder;
    private ByteBuffer byteBuffer;
    private CharBuffer charBuffer;

    /**
     * Конструктор каласса.
     *
     * @param substring подстрока.
     */
    public SubstringFinder(String substring) {
        if (substring.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.substring = substring.codePoints().toArray();
        prefixFunction = buildPrefixFunction(this.substring);

    }

    /**
     * Метод, вычисляющий префикс-функцию подстроки.
     *
     * @param substring подстрока.
     * @return массив-результат префикс-функции.
     */
    private int[] buildPrefixFunction(int[] substring) {
        int[] pi = new int[substring.length];
        for (int i = 1; i < substring.length; i++) {
            int j = pi[i - 1];
            while (j > 0 && substring[i] != substring[j]) {
                j = pi[j - 1];
            }
            if (substring[i] == substring[j]) {
                ++j;
            }
            pi[i] = j;
        }
        return pi;
    }

    /**
     * Метод поиска подстроки в файле.
     *
     * @param path путь до файла.
     * @return список индексов начала каждого вхождения.
     * @throws IOException исключение ввода-вывода.
     */
    public List<Long> find(Path path) throws IOException {
        j = 0;
        position = 0;
        result = new ArrayList<>();

        try (FileChannel ch = FileChannel.open(path, StandardOpenOption.READ)) {
            channel = ch;
            decoder = StandardCharsets.UTF_8.newDecoder().onMalformedInput(
                    CodingErrorAction.REPORT
            ).onUnmappableCharacter(
                    CodingErrorAction.REPORT
            );
            byteBuffer = ByteBuffer.allocate(4096);
            charBuffer = CharBuffer.allocate(4096);

            decoding();
        }

        return new ArrayList<>(result);
    }

    /**
     * Метод, декодирующий байты в символы.
     *
     * @throws IOException исключение ввода-вывода.
     */
    private void decoding() throws IOException {
        boolean endOfInput = false;

        while (!endOfInput) {
            int bytesRead = channel.read(byteBuffer);
            endOfInput = bytesRead == -1;

            byteBuffer.flip();

            CoderResult decodingResult;

            do {
                decodingResult = decoder.decode(
                        byteBuffer,
                        charBuffer,
                        endOfInput
                );

                if (decodingResult.isError()) {
                    decodingResult.throwException();
                }

                processCharacters(
                        charBuffer,
                        endOfInput && decodingResult.isUnderflow()
                );

            } while (decodingResult.isOverflow());

            byteBuffer.compact();
        }

        CoderResult flushingResult;

        do {
            flushingResult = decoder.flush(charBuffer);

            if (flushingResult.isError()) {
                flushingResult.throwException();
            }

            processCharacters(charBuffer, true);

        } while (flushingResult.isOverflow());
    }

    /**
     * Метод для обработки буфера.
     *
     * @param buffer буфер символов.
     * @param endOfInput флаг об окончании файла.
     * @throws CharacterCodingException исключение символьного кода.
     */
    private void processCharacters(CharBuffer buffer, boolean endOfInput)
            throws CharacterCodingException {
        buffer.flip();

        while (buffer.hasRemaining()) {
            char symb = buffer.get();
            int s;

            if (Character.isHighSurrogate(symb)) {
                if (!buffer.hasRemaining()) {
                    if (endOfInput) {
                        throw new CharacterCodingException();
                    }
                    buffer.position(buffer.position() - 1);
                    break;
                }
                else if (Character.isLowSurrogate(buffer.get(buffer.position()))) {
                    s = Character.toCodePoint(symb, buffer.get());
                }
                else {
                    throw new CharacterCodingException();
                }
            }
            else if (Character.isLowSurrogate(symb)) {
                throw new CharacterCodingException();
            }
            else {
                s = symb;
            }

            processCodePoint(s);
        }

        buffer.compact();
    }

    /**
     * Метод обработки символа.
     *
     * @param codePoint символ в числовом представлении.
     */
    private void processCodePoint(int codePoint) {
        while (j > 0 && codePoint != substring[j]) {
            j = prefixFunction[j - 1];
        }
        if (codePoint == substring[j]) {
            j++;
        }
        if (j == substring.length) {
            result.add(position - substring.length + 1);
            j = prefixFunction[j - 1];
        }
        position++;
    }
}
