package com.example.fixparsing;

import java.io.IOException;

public class SimpleFixMessageWriter implements FixMessageWriter {
    private final StringBuilder sb = new StringBuilder();

    @Override
    public void write(final String key, final String val) throws IOException {
        sb.append(key).append(" => ").append(val).append(System.lineSeparator());
    }

}
