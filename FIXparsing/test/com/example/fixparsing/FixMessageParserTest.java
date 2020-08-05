package com.example.fixparsing;

import com.google.common.truth.Truth;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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

    @ParameterizedTest
    @ValueSource(strings = {FixMessage.NEW_ORDER_SINGLE, FixMessage.NEW_ORDER_SINGLE_1})
    void testSplitMessage(String message) throws FixMessageParser.ParseException {
        System.out.println(FIXMessageHandling.splitMessage(message));
    }

    @Test
    void testJsonFormat(){
        String str = "{\"content\":\"this is the msg content.\",\"tousers\":\"user1|user2\",\"msgtype\":\"texturl\",\"appkey\":\"test\",\"domain\":\"test\","
                + "\"system\":{\"wechat\":{\"safe\":\"1\"}},\"texturl\":{\"urltype\":\"0\",\"user1\":{\"spStatus\":\"user01\",\"workid\":\"work01\"},\"user2\":{\"spStatus\":\"user02\",\"workid\":\"work02\"}}}";
        System.out.println(JsonFormatUtils.formatJson(str));
    }

    @Test
    // FIXME fix test error and make it run
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
