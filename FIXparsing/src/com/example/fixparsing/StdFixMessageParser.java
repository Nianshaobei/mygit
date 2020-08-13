package com.example.fixparsing;

import org.apache.log4j.Logger;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Map;

class StdFixMessageParser implements FixMessageParser {

    private static Logger logger = Logger.getLogger(StdFixMessageParser.class);

    @Override
    public void parse(
            @Nullable final String input,
            final FixMessageWriter writer,
            final Map<Integer, FixTagTranslator> customTagTranslators
    ) throws ParseException, IOException {
        // TODO add implementation here

        if (null != input) {
            Map<String, String> tagvalue = FIXMessageHandling.splitMessage(input);

            for (Map.Entry<String, String> entry : tagvalue.entrySet()) {
                final String key = entry.getKey();
                final String val = entry.getValue();
                String tagTrans;
                String valueTrans;

                try {
                    int keyint = Integer.parseInt(key);

                    if (null != customTagTranslators.get(keyint)) {
                        tagTrans = customTagTranslators.get(keyint).getName();
                        valueTrans = customTagTranslators.get(keyint).translateValue(val);
                    } else {
                        tagTrans = key;
                        valueTrans = val;
                    }

                    writer.write(tagTrans, valueTrans);

                } catch (NumberFormatException e) {
                    logger.error("Failed to convert integer type.", e);
                }

            }
        }

    }

}
