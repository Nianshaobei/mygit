package com.example.fixparsing;

public class StdFixTag56Translator implements FixTagTranslator {
    @Override
    public String getName() {
        return "接收方ID";
    }

    @Override
    public String translateValue(String value) {
        return value;
    }
}
