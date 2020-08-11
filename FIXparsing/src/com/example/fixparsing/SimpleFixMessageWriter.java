package com.example.fixparsing;

import java.io.IOException;
import java.io.Writer;

public class SimpleFixMessageWriter implements FixMessageWriter {

    private Writer writer;

    @Override
    public void write(final String key, final String val) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(key).append(" => ").append(val).append(System.lineSeparator());
        writer.write(String.valueOf(sb));
    }

    SimpleFixMessageWriter(Writer writer) {
        this.writer = writer;
    }


}
