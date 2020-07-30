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

        final String expected = "{\n" +
                "\t\"订单方\":\"买方\",\n" +
                "\t\"版本信息\":\"FIX.4.4\",\n" +
                "\t\"订单类型\":\"市价订单\",\n" +
                "\t\"消息类型\":\"新订单\",\n" +
                "\t\"订单总数\":\"5000\",\n" +
                "\t\"交易时间\":\"2003061501:14:49\",\n" +
                "\t\"消息长度\":\"100\",\n" +
                "\t\"校验和\":\"127\",\n" +
                "\t\"客户端订单ID\":\"12345\"\n" +
                "}";

        FixMessageWriter writer = new JsonFixMessageWriter();
        parser.parse(input, writer);

        Truth.assertThat(((JsonFixMessageWriter) writer).getPrettyJson()).isEqualTo(expected);
    }

    @Test
        // FIXME fix test error and make it run
    void testValidFixMessages() throws Exception {
        final String input = FixMessage.NEW_ORDER_SINGLE;

        final String expected = "协议版本:FIX.4.4\n" +
                "消息体长度:100\n" +
                "消息类型:新订单\n" +
                "客户端订单ID:12345\n" +
                "订单类型:市价订单\n" +
                "订单方:买方\n" +
                "订单总数:5000\n" +
                "交易时间:2003061501:14:49\n" +
                "校验和:127";

        final StringBuilder sb = new StringBuilder();

        parser.parse(input, sb);

        Truth.assertThat(sb.toString()).isEqualTo(expected);
    }

    @Test
    void testInvalidFixMessages() throws Exception {
        final String[] input = {
                "123",
                "8=2"
        };

        for (String s : input) {
            final StringBuilder sb = new StringBuilder();

            Assertions.assertThrows(
                    FixMessageParser.ParseException.class,
                    () -> parser.parse(s, sb)
            );
        }

    }
}