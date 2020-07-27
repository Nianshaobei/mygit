package com.example.fixparsing;

public class StdFixTag60Translator implements FixTagTranslator {
    @Override
    public String getName() {
        return "交易时间";
    }

    @Override
    public String translateValue(String value) {
        return value;
    }
}
