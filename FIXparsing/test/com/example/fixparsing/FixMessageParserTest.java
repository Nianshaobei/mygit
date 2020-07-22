package com.example.fixparsing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

class FixMessageParserTest {
    private FixMessageParser parser;
    FixMessage message = new FixMessage();
    FIXMessageHandling handling = new FIXMessageHandling();
    @BeforeEach
    void setUp() {
        // TODO use your implementation here
        parser = new FixMessageParser() {
            @Override
            public void parse(@Nullable String input, StringBuilder output) throws FixMessageParser.ParseException {
                if(!handling.isValid(input)){
                    throw new FixMessageParser.ParseException(input);
                }
                System.out.println(output);
            }
        };
    }

    @Test
    void testFixNewOrderSingle () {
        try{
            parser.parse(message.newOrderSingle, handling.Translate(message.newOrderSingle));
        }catch (FixMessageParser.ParseException e ){
            System.out.println("Invilid FIX message!");
        }
    }

    @Test
    void testInvalidFixMessages() throws Exception {
        final String[] input = {
                "123",
                "0=2"
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