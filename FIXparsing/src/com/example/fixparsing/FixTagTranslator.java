package com.example.fixparsing;

public interface FixTagTranslator {
    String getName();

    String translateValue(final String value);
}
