package com.example.fixparsing;

import org.junit.jupiter.api.Test;

class FIXMessageTest {
  @Test
  void testFixNewOrderSingle() {
    FixMessage message = new FixMessage();
    FIXMessageHandling handling = new FIXMessageHandling();
    handling.Translate(message.newOrderSingle);
  }
}
