package com.example.fixparsing;

public class StdFixTag49Translator implements FixTagTranslator {
    @Override
    public String getName() {
        return "发送方ID";
    }

    @Override
    public String translateValue(String value) {
        return value;
    }
}
