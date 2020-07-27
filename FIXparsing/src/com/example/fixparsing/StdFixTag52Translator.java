package com.example.fixparsing;

public class StdFixTag52Translator implements FixTagTranslator {
    @Override
    public String getName() {
        return "发送时间";
    }

    @Override
    public String translateValue(String value) {
        return value;
    }
}
