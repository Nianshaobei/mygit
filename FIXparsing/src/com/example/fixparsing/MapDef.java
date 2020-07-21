package com.example.fixparsing;

import java.util.HashMap;
import java.util.Map;

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

    public Map<String,String> getValueMessageMap(){
        Map<String, String> valueMessageMap = new HashMap<>();
        valueMessageMap.put("350", "心跳");
        valueMessageMap.put("351", "测试请求");
        valueMessageMap.put("352", "重发请求");
        valueMessageMap.put("353", "回绝");
        valueMessageMap.put("354", "序列号重置");
        valueMessageMap.put("355", "登出");
        valueMessageMap.put("357", "公告");
        valueMessageMap.put("358", "执行报告");
        valueMessageMap.put("35A", "登入");
        valueMessageMap.put("35B", "消息");
        valueMessageMap.put("35D", "新订单");
        valueMessageMap.put("35F", "订单撤销请求");
        valueMessageMap.put("35H", "订单状况请求");
        valueMessageMap.put("35R", "报价请求");
        valueMessageMap.put("35S", "报价确认");

        valueMessageMap.put("980", "无/其他");
        valueMessageMap.put("981", "PKCS");
        valueMessageMap.put("982", "DES");
        valueMessageMap.put("983", "PKCS/DES");
        valueMessageMap.put("984", "PGP/DES");

        valueMessageMap.put("141Y", "是");
        valueMessageMap.put("141N", "否");

        valueMessageMap.put("401", "市价订单");
        valueMessageMap.put("402", "限价订单");

        valueMessageMap.put("541", "买方");
        valueMessageMap.put("542", "卖方");
        valueMessageMap.put("545", "卖空");


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
