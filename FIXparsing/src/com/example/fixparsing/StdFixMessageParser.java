package com.example.fixparsing;

import javax.annotation.Nullable;
import java.util.Map;

class StdFixMessageParser implements FixMessageParser {
    @Override
    public void parse(
            @Nullable final String input,
            final StringBuilder output,
            final Map<Integer, FixTagTranslator> customTagTranslators
    ) throws ParseException {
        // TODO add implementation here
    }
}
