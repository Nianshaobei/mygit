package com.example.fixparsing;

public class StdFixTag10Translator implements FixTagTranslator {
    @Override
    public String getName() {
        return "校验和";
    }

    @Override
    public String translateValue(String value) {
        return value;
    }
}
