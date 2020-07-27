package com.example.fixparsing;

public class StdFixTag11Translator implements FixTagTranslator {
    @Override
    public String getName() {
        return "客户端订单ID";
    }

    @Override
    public String translateValue(String value) {
        return value;
    }
}
