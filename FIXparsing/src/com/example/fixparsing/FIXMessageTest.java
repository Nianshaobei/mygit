package com.example.fixparsing;

public class FIXMessageTest {

    public static void main(String[] args){
        FixMessage message = new FixMessage();
        FIXMessageHandling handling = new FIXMessageHandling();
        handling.Translate(message.newOrderSingle);
    }
}
