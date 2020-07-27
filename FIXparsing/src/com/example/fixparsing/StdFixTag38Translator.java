package com.example.fixparsing;

public class StdFixTag38Translator implements FixTagTranslator {
    @Override
    public String getName() {
        return "订单总数";
    }

    @Override
    public String translateValue(String value) {
        return value;
    }
}
