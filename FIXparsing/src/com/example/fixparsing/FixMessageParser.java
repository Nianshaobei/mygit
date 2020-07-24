package com.example.fixparsing;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Map;

/**
 * TODO implement this interface
 */
public interface FixMessageParser {

    /**
     * Parse a string {@code input} as FIX message and write the parsed result into {@code output}.
     *
     * @param input  FIX message (may be null or invalid
     * @param output string output
     * @throws ParseException if not valid FIX message or tag not recognized
     */
    default void parse(@Nullable final String input, final StringBuilder output) throws ParseException {
        parse(input, output, Collections.emptyMap());
    }

    default void parse(
            @Nullable final String input, final StringBuilder output,
            final Map<Integer, FixTagTranslator> customTagTranslators
    ) throws ParseException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public static class ParseException extends Exception {
        public ParseException(String message) {
            super(message);
        }

        public ParseException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}