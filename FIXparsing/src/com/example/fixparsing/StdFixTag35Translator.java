package com.example.fixparsing;

class StdFixTag35Translator implements FixTagTranslator {
    @Override
    public String getName() {
        return "消息类型";
    }

    @Override
    public String translateValue(String value) {
        switch (value) {
            case "0":
                return "心跳";
            case "1":
                return "测试请求";
            case "2":
                return "重发请求";
            case "3":
                return "回绝";
            case "4":
                return "序列号重置";
            case "5":
                return "登出";
            case "7":
                return "公告";
            case "8":
                return "执行报告";
            case "A":
                return "登入";
            case "B":
                return "消息";
            case "D":
                return "新订单";
            case "F":
                return "订单撤销请求";
            case "H":
                return "订单状况请求";
            case "R":
                return "报价请求";
            case "S":
                return "报价确认";
            default:
                return "未知";
        }
    }
}
