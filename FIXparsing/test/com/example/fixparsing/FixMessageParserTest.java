package com.example.fixparsing;

import com.google.common.truth.Truth;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class FixMessageParserTest {
    private FixMessageParser parser;

    @BeforeEach
    void setUp() {
        parser = new StdFixMessageParser();
    }

    @Test
    void testFixNewOrderSingletoJson() throws IOException, FixMessageParser.ParseException {
        final String input = FixMessage.NEW_ORDER_SINGLE;

        Dom4JReaderUtils.resource = "src\\FIX.xml";

        final String expected = "{\n" +
                "\t\"BeginString\":\"FIX.4.4\",\n" +
                "\t\"BodyLength\":\"100\",\n" +
                "\t\"MsgType\":\"ORDER_SINGLE\",\n" +
                "\t\"ClOrdID\":\"12345\",\n" +
                "\t\"OrdType\":\"MARKET\",\n" +
                "\t\"Side\":\"BUY\",\n" +
                "\t\"OrderQty\":\"5000\",\n" +
                "\t\"TransactTime\":\"2003061501:14:49\",\n" +
                "\t\"CheckSum\":\"127\"\n" +
                "}";

        FixMessageWriter writer = new JsonFixMessageWriter();
        parser.parse(input, writer);

        Truth.assertThat(((JsonFixMessageWriter) writer).getFormatJson()).isEqualTo(expected);
    }

    @Test
        // FIXME fix test error and make it run
    void testFixNewOrderSingletoString() throws Exception {
        final String input = FixMessage.NEW_ORDER_SINGLE;

        Dom4JReaderUtils.resource = "src\\FIX.xml";

        final String expected = "BeginString => FIX.4.4\r\n" +
                "BodyLength => 100\r\n" +
                "MsgType => ORDER_SINGLE\r\n" +
                "ClOrdID => 12345\r\n" +
                "OrdType => MARKET\r\n" +
                "Side => BUY\r\n" +
                "OrderQty => 5000\r\n" +
                "TransactTime => 2003061501:14:49\r\n" +
                "CheckSum => 127\r\n";

        FixMessageWriter writer = new SimpleFixMessageWriter();
        parser.parse(input, writer);

        Truth.assertThat(((SimpleFixMessageWriter) writer).getSb().toString()).isEqualTo(expected);
    }

    @Test
    void testInvalidFixMessages() throws Exception {
        final String[] input = {
                "123",
                "8==2"
        };

        for (String s : input) {

            FixMessageWriter writer = new JsonFixMessageWriter();

            Assertions.assertThrows(
                    FixMessageParser.ParseException.class,
                    () -> parser.parse(s, writer)
            );
        }

    }
}
