package com.example.fixparsing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FIXMessageTest {
    @Test
    void testFixNewOrderSingle() {
        FixMessage message = new FixMessage();
        FIXMessageHandling handling = new FIXMessageHandling();
        handling.Translate(message.newOrderSingle);
    }

    @Test
    void testInvalidFixMessages() {
        final String[] input = {
                "123"
        };

        for (String s : input) {
            final FIXMessageHandling handling = new FIXMessageHandling();

            // TODO fix this bug
            Assertions.assertThrows(
                    ArrayIndexOutOfBoundsException.class,
                    () -> handling.Translate(s)
            );
        }
    }
}
