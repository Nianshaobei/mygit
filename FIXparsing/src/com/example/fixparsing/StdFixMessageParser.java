package com.example.fixparsing;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Map;

class StdFixMessageParser implements FixMessageParser {

    @Override
    public void parse(
            @Nullable final String input,
            final FixMessageWriter writer,
            final Map<Integer, FixTagTranslator> customTagTranslators,
            final String resource
    ) throws ParseException, IOException {
        // TODO add implementation here

        final Map<Integer, FixTagTranslator> builtinTranslators = XmlParserUtils.loadBuiltinTranslators(resource);
        builtinTranslators.putAll(customTagTranslators);

        assert input != null;
        Map<String, String> tagvalue = FIXMessageHandling.splitMessage(input);

        for (Map.Entry<String, String> entry : tagvalue.entrySet()) {
            final String key = entry.getKey();
            final String val = entry.getValue();
            String tagTrans = "", valueTrans = "";

            try {
                int keyint = Integer.parseInt(key);

                if (builtinTranslators.get(keyint) != null) {
                    tagTrans = builtinTranslators.get(keyint).getName();
                    valueTrans = builtinTranslators.get(keyint).translateValue(val);
                }

                writer.write(tagTrans, valueTrans);

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        }
    }

}
