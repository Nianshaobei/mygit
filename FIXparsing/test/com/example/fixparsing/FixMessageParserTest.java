package com.example.fixparsing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FixMessageParserTest {
    private FixMessageParser parser;

    @BeforeEach
    void setUp() {
        // TODO use your implementation here
        parser = new FixMessageParser() {
        };
    }

    @Test
    void testInvalidFixMessages() throws Exception {
        final String[] input = {
                "123"
        };

        for (String s : input) {
            final StringBuilder sb = new StringBuilder();

            Assertions.assertThrows(
                    FixMessageParser.ParseException.class,
                    () -> parser.parse(s, sb)
            );
        }
    }
}