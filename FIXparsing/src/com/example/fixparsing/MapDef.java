package com.example.fixparsing;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class MapDef {

    public Map<String,String> getTagMessageMap(){
        Map<String, String> tagMessageMap = new HashMap<>();
        tagMessageMap.put("8", "协议版本");
        tagMessageMap.put("9", "消息体长度");
        tagMessageMap.put("10", "校验和");
        tagMessageMap.put("34", "消息序列号");
        tagMessageMap.put("35", "消息类型");
        tagMessageMap.put("49", "发送方ID");
        tagMessageMap.put("52", "发送时间");
        tagMessageMap.put("56", "接受方ID");
        tagMessageMap.put("98", "加密方式");
        tagMessageMap.put("108", "心跳阈");
        tagMessageMap.put("11", "客户端订单ID");
        tagMessageMap.put("40", "订单类型");
        tagMessageMap.put("54", "订单方");
        tagMessageMap.put("38", "订单总数");
        tagMessageMap.put("60", "交易时间");
        return tagMessageMap;
    }

    public Map<String,Function<String, String>> getValueMessageMap(){
        Map<String, Function<String, String>> valueMessageMap = new HashMap<>();
        valueMessageMap.put("35", new Function<String, String>() {
            @Override
            public String apply(String s) {
                switch (s){
                    case "0" : return "心跳";
                    case "1" : return "测试请求";
                    case "2" : return "重发请求";
                    case "3" : return "回绝";
                    case "4" : return "序列号重置";
                    case "5" : return "登出";
                    case "7" : return "公告";
                    case "8" : return "执行报告";
                    case "A" : return "登入";
                    case "B" : return "消息";
                    case "D" : return "新订单";
                    case "F" : return "订单撤销请求";
                    case "H" : return "订单状况请求";
                    case "R" : return "报价请求";
                    case "S" : return "报价确认";
                    default : return "未知";
                }
            }
        });



        valueMessageMap.put("98", s -> {
            switch (s){
                case "0" : return "无/其他";
                case "1" : return "PKCS";
                case "2" : return "DES";
                case "3" : return "PKCS/DES";
                case "4" : return "PGP/DES";
                default : return "未知";
            }
        });

        valueMessageMap.put("141", s -> {
            switch (s){
                case "Y" : return "是";
                case "N" : return "否";
                default : return "未知";
            }
        });

        valueMessageMap.put("40", s -> {
            switch (s){
                case "1" : return "市价订单";
                case "2" : return "限价订单";
                default : return "未知";
            }
        });

        valueMessageMap.put("54", s -> {
            switch (s){
                case "1" : return "买方";
                case "2" : return "卖方";
                case "5" : return "卖空";
                default : return "未知";
            }
        });

        return valueMessageMap;
    }

    public boolean getTagFlag(String tag){
        String[] extendTags = {"35", "98", "141", "40", "54"};
        for(String extendTag : extendTags){
            if(tag.equals(extendTag))
                return true;
        }
        return false;
    }

}
