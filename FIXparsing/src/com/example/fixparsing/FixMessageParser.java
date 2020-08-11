package com.example.fixparsing;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Map;

/**
 * TODO implement this interface
 */
public interface FixMessageParser {

    /**
     * Parse a string {@code input} as FIX message and write the parsed result into {@code output}.
     *
     * @param input  FIX message (may be null or invalid
     * @param writer string output
     * @throws ParseException if not valid FIX message or tag not recognized
     */
    default void parse(
            @Nullable final String input,
            final FixMessageWriter writer,
            final Map<Integer, FixTagTranslator> customTagTranslators
    ) throws ParseException, IOException {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    class ParseException extends Exception {
        public ParseException(String message) {
            super(message);
        }

        public ParseException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}