package com.example.fixparsing;

public class StdFixTag34Translator implements FixTagTranslator {
    @Override
    public String getName() {
        return "消息序列号";
    }

    @Override
    public String translateValue(String value) {
        return value;
    }
}
