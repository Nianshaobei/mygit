package com.example.fixparsing;

public class StdFixTag108Translator implements FixTagTranslator {
    @Override
    public String getName() {
        return "心跳阈";
    }

    @Override
    public String translateValue(String value) {
        return value;
    }
}
