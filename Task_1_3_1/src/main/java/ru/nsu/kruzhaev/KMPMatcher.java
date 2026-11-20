package ru.nsu.kruzhaev;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.*;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class KMPMatcher {
    private final int[] pattern;
    private final int[] prefixFunction;
    private int j;
    private long position;
    private List<Long> result;
    private FileChannel channel;
    private CharsetDecoder decoder;
    private ByteBuffer byteBuffer;
    private CharBuffer charBuffer;

    public KMPMatcher(String pattern) {
        if (pattern.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.pattern = pattern.codePoints().toArray();
        prefixFunction = buildPrefixFunction(this.pattern);

    }

    private int[] buildPrefixFunction(int[] pattern) {
        int[] pi = new int[pattern.length];
        for (int i = 1; i < pattern.length; i++) {
            int j = pi[i - 1];
            while (j > 0 && pattern[i] != pattern[j]) {
                j = pi[j - 1];
            }
            if (pattern[i] == pattern[j]) {
                ++j;
            }
            pi[i] = j;
        }
        return pi;
    }

    public List<Long> find(Path path) throws IOException {
        j = 0;
        position = 0;
        result = new ArrayList<>();

        try (FileChannel ch = FileChannel.open(path, StandardOpenOption.READ)){
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

    private void processCharacters(CharBuffer buffer, boolean endOfInput) throws CharacterCodingException {
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

//    public List<Long> find(String text) {
//        int[] pointedText = text.codePoints().toArray();
//        int j = 0;
//
//        for (int i = 0; i < pointedText.length; i++) {
//            while (j > 0 && pointedText[i] != pattern[j]) {
//                j = prefixFunction[j - 1];
//            }
//            if (pointedText[i] == pattern[j]) {
//                j++;
//            }
//            if (j == pattern.length) {
//                result.add((long) i - pattern.length + 1);
//                j = prefixFunction[j - 1];
//            }
//        }
//
//        return result;
//    }

    private void processCodePoint(int codePoint) {
        while (j > 0 && codePoint != pattern[j]) {
            j = prefixFunction[j - 1];
        }
        if (codePoint == pattern[j]) {
            j++;
        }
        if (j == pattern.length) {
            result.add(position - pattern.length + 1);
            j = prefixFunction[j - 1];
        }
        position++;
    }

    public int[] getPrefixFunction() {
        return prefixFunction;
    }
}
